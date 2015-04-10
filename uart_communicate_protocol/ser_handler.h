#ifndef __SER_HANDLER_H
#define __SER_HANDLER_H
#include "ser_cmd.h"

extern const uint_8 cmd_np_index;
#define CMD_NP_INDEX (sizeof(ser_rcvcmd_funcs)/sizeof(CMD_FUN_MAP)-2

err_code ser_cmd_discover(PKT_DATA** pkt, void *input, uint_32 len);
err_code ser_cmd_send_05(PKT_DATA** pkt, void *input, uint_32 len);
err_code ser_cmd_notimplement(PKT_DATA** pkt, void *input, uint_32 len);

#endif

