/* USAGE:
err_code ser_cmd_init(void) 
void ser_cmd_settimeout(uint_32 timeout)
err_code ser_cmd_send(uint_8 cmd, void *pkt_data, uint_32 len, PKT** reply)
    //reply in *reply,if reply == NULL, means don't process reply;
    //useful macro: PKT_DATALEN(pkt), PKT_DATA(pkt), PKT_FREE(pkt)
err_code ser_cmd_rcv(void)
    //rcv bytes in uartbuf, assemble to pkt, tell to rcv/send functions
    
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

#include "ser_cmd.h"
#include "ser_hal.h"
#include "ser_handler.h"
#include "debug.h"
#include "types.h"

#define NET_ENDIAN 1    //big endian

#define MEM_U8(p, oft) (*(((uint_8*)(p))+(oft)))
#define MEM_U16(p, oft) (*(((uint_16*)(p))+(oft)))
#define MEM_U32(p, oft) (*(((uint_32*)(p))+(oft)))

#define MAC_CMP_HEAD(head) (memcmp((void*)head, _mac_head, 2))
#define MAC_CPY_HEAD(head) (memcpy((void*)head, _mac_head, 2))

#define IS_REPLY(pkt) (((pkt).app_hdr.cmd)&REPLY_MASK)

extern CMD_FUN_MAP ser_rcvcmd_funcs[];

static const char _mac_head[2] = {'@', '>'};
static uint_8 _send_sn = 0;
static uint_32 _timeout = 100;


/*********local functions ************/

static uint_8 _ser_calc_checksum(PKT *pkt)
{
    int i;
    uint_8 sum;
    uint_8* buf = (uint_8*)pkt;
    uint_8 len = pkt->mac_hdr.len;
    
    for(i = 0, sum = 0; i < len; i++)
	{
		sum ^= MEM_U8(buf, i);
	}
    sum ^= (pkt->mac_hdr.check_sum);
    return sum;
}

static err_code _mac_parse(PKT *pkt)
{
    int sum;
	//head
	if(MAC_CMP_HEAD(pkt->mac_hdr.head)) return err_fmt;
	//check sum
    sum = _ser_calc_checksum(pkt);
	if(sum != pkt->mac_hdr.check_sum) return err_sum;
	return err_ok;
}

static int _find_handler(CMD_FUN_MAP* map, uint_8 cmd)
{
    int i = 0;

    while (1)
    {
        if (map[i].cmd == cmd)
            return i;
        if (map[i].cmd == 0 && map[i].func == NULL)
            break;
        i++;
    }
    return -1;
}

static void _build_pkt(uint_8 cmd, uint_8 sn, PKT *pkt, void *pkt_data, uint_32 len)
{   //mac
    MAC_CPY_HEAD(pkt->mac_hdr.head);
    //TODO:check len
    pkt->mac_hdr.len = HDR_LEN + len;
    //trans
    pkt->trans_hdr.sn = sn;
    //app
    pkt->app_hdr.cmd = cmd;
    if (pkt_data)
    {
        memcpy(pkt->data, pkt_data, len);
    }
    pkt->mac_hdr.check_sum = _ser_calc_checksum(pkt);
    return;
}


/******public functions *******/
err_code ser_cmd_init(void)
{
    ser_hal_init();
    return err_ok;
}

err_code ser_cmd_reply(PKT* pkt, uint_8 cmd, void *pkt_data, uint_32 len)
{
    uint_8 sn;
    PKT *new_pkt;

    if (!pkt)
    {
        return err_err;
    }
    new_pkt = (PKT *)ser_hal_malloc(PKT_SIZE + len);
    if (!new_pkt)
    {
        return err_nomem;
    }
    sn = pkt->trans_hdr.sn;
    _build_pkt(cmd, sn, new_pkt, pkt_data, len);

    ser_hal_write(new_pkt, new_pkt->mac_hdr.len);
    ser_hal_free(new_pkt);
    return err_ok;
}

void ser_cmd_settimeout(uint_32 timeout)
{
    _timeout = timeout;
    return;
}

err_code ser_cmd_send(uint_8 cmd, void *pkt_data, uint_32 len, PKT** reply)
{
    PKT *pkt;
    err_code res, ret_res;
    //build pkt
    pkt = (PKT *)(ser_hal_malloc(PKT_SIZE + len));
    if (!pkt)
    {
        return err_nomem;
    }
    _build_pkt(cmd, _send_sn, pkt, pkt_data, len);
    _send_sn++;
    
    //send & wait reply
    ser_hal_write(pkt, PKT_SIZE + len);
    res = ser_hal_rcvreply(reply, pkt->trans_hdr.sn, _timeout);
    if(res != err_ok) 
    {
        ret_res = res;
        goto out0;
    }
	//deal reply in app
out0:
    ser_hal_free(pkt);
    return ret_res;
}

err_code ser_cmd_rcv(void)
{
    static uint_8 head[MAC_HDR_LEN] = {0};
    static uint_8* buf = NULL;
    static uint_8 ch, pkt_len;
    static uint_8 i = 0;
    err_code res;

    while (ser_hal_read(&ch, 1))
    {
        //debug_trace("rcv ch %02x\n", ch);
        if(i <= 2)  //store to head
        {
            head[i] = ch;
            if (i <= 1 && head[i] != _mac_head[i])
            {
                i = 0;
                continue;
            }
            if (i == 2)
            {
                pkt_len = ch;
                if(pkt_len < HDR_LEN)
                {
                    i = 0;
                    continue;
                }
                else    //alloc buf
                {
                    buf = ser_hal_malloc(pkt_len);
                    if(buf == NULL) 
                    {
                        i = 0;
                        continue;
                    }
                    else    //copy to buf
                    {
                        memcpy(buf, head, 3);
                    }
                }
            }
        }
        else    //store to buf
        {
            buf[i] = ch;
            if (i == pkt_len - 1)
            {
                //_dump(buf, pkt_len);
                //debug_trace("get new packet\n");
                if ((res = _mac_parse((PKT *)buf)) != err_ok)
                {   //check sum failed
                    i = 0;
                    ser_hal_free(buf);
                    continue;
                }
                else
                {
                    if (IS_REPLY(*((PKT *)buf)))
                    {
                        ser_hal_tellsend((PKT *)buf);   //check sn, and tell send
                    }
                    else
                    {
                        ser_cmd_dealcmd((PKT *)buf);
                        printf("\n");
                        ser_hal_free(buf);
                    }
                    i = 0;
                    buf = NULL;
                    continue;
                }
            }
        }
        i++;
    }
    return err_ok;
}

// maybe need to be public interface
err_code ser_cmd_dealcmd(PKT *pkt)
{
    uint_8 cmd;
    int fun_index;
    int res_code;
    CMD_FUNC fun;
    PKT_DATA *data_out = NULL;
    
    if (!pkt) 
    {
        return err_err;
    }
    _dump(pkt, pkt->mac_hdr.len);
    cmd = pkt->app_hdr.cmd;
    fun_index = _find_handler(ser_rcvcmd_funcs, cmd);
    //debug_trace("index %d\n", fun_index);
    if (fun_index < 0) fun_index = cmd_np_index;    //not implement index
    fun = ser_rcvcmd_funcs[fun_index].func;
    
    res_code = fun(&data_out, pkt->data, PKT_DATALEN(pkt));
    if (res_code != err_ok)
    {
        debug_error("error!");
        data_out = NULL;
    }
    
    // data_out can be NULL
    if (data_out)
    {
        ser_cmd_reply(pkt, res_code | REPLY_MASK, data_out->data, data_out->len);
    }
    else
    {
        ser_cmd_reply(pkt, res_code | REPLY_MASK, NULL, 0);
    }
    ser_hal_free(data_out);
    return err_ok;
}



