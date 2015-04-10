#ifndef __SER_CMD1_H
#define __SER_CMD1_H


#include "types.h"

typedef struct _mac_hdr
{
    uint_8 head[2];
    uint_8 len;
    uint_8 check_sum;
} MAC_HDR;

typedef struct _trans_hdr
{
    uint_8 sn;
} TRANS_HDR;


typedef struct _app_hdr
{
    uint_8 cmd;
} APP_HDR;


#define MAC_HDR_LEN sizeof(MAC_HDR)
#define TRANS_HDR_LEN sizeof(TRANS_HDR)
#define APP_HDR_LEN sizeof(APP_HDR)
#define HDR_LEN (MAC_HDR_LEN+TRANS_HDR_LEN+APP_HDR_LEN)

#define REPLY_MASK 0x80
#define SER_BUF_MAX 256

typedef struct _pkt
{
    MAC_HDR mac_hdr;
    TRANS_HDR trans_hdr;
    APP_HDR app_hdr;
    uint_8 data[1];
} PKT;


typedef struct _pkt_data
{
    uint_8 len;
    uint_8 data[1];
} PKT_DATA;

#define PKT_SIZE (sizeof(PKT) - 1)
#define PKT_DATA_SIZE (sizeof(PKT_DATA) - 1)

#define PKT_DATALEN(pkt) (((pkt)->mac_hdr.len) - (HDR_LEN))
#define PKT_DATA(pkt) ((pkt)->data)
#define PKT_FREE(pkt) ser_hal_free(pkt);

typedef enum _err_code{
	err_ok = 0,
	err_err,
	err_sum,
	err_fmt,
	err_ver,
	err_nomem,
	err_noans,
	err_busy,
	err_notimplement
} err_code;


typedef err_code(*CMD_FUNC)(PKT_DATA**, void *input, uint_32 len);

typedef struct _cmd_fun
{
	uint_8 cmd;
	CMD_FUNC func;
} CMD_FUN_MAP;


err_code ser_cmd_init();
void ser_cmd_settimeout(uint_32 timeout);
err_code ser_cmd_send(uint_8 cmd, void *pkt_data, uint_32 len, PKT** reply);
err_code ser_cmd_rcv();
err_code ser_cmd_dealcmd();


#endif

