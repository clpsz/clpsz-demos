#ifndef __SER_HAL_H
#define __SER_HAL_H


#include "ser_cmd.h"

#define HOST_ENDIAN 0   //little endian


typedef uint_8 UART;


void *ser_hal_malloc(unsigned int size);
void ser_hal_free(void *ptr);

void ser_hal_init();
int ser_hal_read(void *c, unsigned int count);
void ser_hal_write(void *buf, unsigned int len);
//void ser_hal_notesend(int s, void *buf);
//void ser_hal_notercv(void *buf);

err_code ser_hal_tellsend(PKT *pkt);
err_code ser_hal_rcvreply(PKT** pkt, uint_8 sn, uint_32 timeout);

#endif

