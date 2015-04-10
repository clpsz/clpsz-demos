#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <arpa/inet.h>

#include "ser_cmd.h"
#include "ser_hal.h"
#include "debug.h"

// err_ok: no error
// *pkt != NULL : need free
// caller deals with the memory
err_code ser_cmd_discover(PKT_DATA** pkt, void *input, uint_32 len)
{
    PKT_DATA *pkt_data = (PKT_DATA *)ser_hal_malloc(PKT_DATA_SIZE + 20);
    if (!pkt_data) return err_nomem;

    pkt_data->len = strlen("discover");
    strcpy((char *)pkt_data->data, "discover");
    *pkt = pkt_data;
    return err_ok;
}

// err_ok: no error
// PKT_DTA != NULL : need free
// caller deals with the memory
err_code ser_cmd_send_05(PKT_DATA** pkt, void *input, uint_32 len)
{
    PKT_DATA *pkt_data = (PKT_DATA *)ser_hal_malloc(PKT_DATA_SIZE + 1);
    if (!pkt_data) return err_nomem;

    pkt_data->len = 1;
    pkt_data->data[0] = 5;
    *pkt = pkt_data;
    return err_ok;
}



err_code ser_cmd_notimplement(PKT_DATA** pkt, void *input, uint_32 len)
{
    PKT_DATA *pkt_data = (PKT_DATA *)ser_hal_malloc(PKT_DATA_SIZE + 20);
    if (!pkt_data) return err_nomem;

    pkt_data->len = strlen("notimplement");
    strcpy((char *)pkt_data->data, "notimplement");
    *pkt = pkt_data;
    return err_ok;
}

err_code ser_cmd_echo_back(PKT_DATA** pkt, void *input, uint_32 len)
{
    PKT_DATA *pkt_data = (PKT_DATA *)ser_hal_malloc(PKT_DATA_SIZE + len);
    if (!pkt_data) return err_nomem;

    pkt_data->len = len;
    memcpy(pkt_data->data, input, len);
    *pkt = pkt_data;
    return err_ok;
}


/**********handler map**********/
CMD_FUN_MAP ser_rcvcmd_funcs[] = 
{
    {0x01, ser_cmd_discover},
    {0x02, ser_cmd_send_05},
    {0x61, ser_cmd_echo_back},
    {0x7f, ser_cmd_notimplement},
	{0x00, NULL}
};
const uint_8 cmd_np_index = (sizeof(ser_rcvcmd_funcs)/sizeof(CMD_FUN_MAP)-2);

