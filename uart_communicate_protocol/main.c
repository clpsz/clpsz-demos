#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <termios.h>
#include <errno.h>
#include <pthread.h>
#include <getopt.h>

#include "ser_cmd.h"
#include "ser_hal.h"
#include "ser_handler.h"
#include "debug.h"



#define SEND        0
#define RECEIVE     1
#define USAGE       "USAGE:\n" \
                    "      ./_main -s/-r"

UART g_uartx = 1;


void *ser_cmd_rcv_wrapper(void *cmd)
{
    ser_cmd_rcv();
    
    return NULL;
}


static void _send_10()
{
    unsigned long long card_num = 123456789;
    unsigned int in_time = 12345678;
    unsigned int out_time = 87654321;
    unsigned char *buf[64];

    memcpy(buf, &card_num, 8);
    memcpy(buf + 8, &in_time, 4);
    memcpy(buf + 12, &out_time, 4);

    ser_cmd_send(0x10, buf, 16, NULL);
}




int main(int argc, char *argv[])
{
    int opt;
    int send_or_receive = 0;
    pthread_t pid;
    char *str_to_send;
    
    str_to_send = "default send string";
    //char send_buf[] = "9876543210";

    //debug_trace("@ is %02x, > is %02x\n", '@', '>');
    //printf("%d\n", sizeof(long long));

    while ((opt = getopt(argc, argv, "s:r")) != -1)
    {
        switch (opt)
        {
        case 's':
            str_to_send = optarg;
            g_uartx = 0;
            send_or_receive = SEND;
            break;
        case 'r':
            g_uartx = 1;
            send_or_receive = RECEIVE;
            break;
        case '?':
            printf("%s\n", USAGE);
            exit(0);
            break;
        default:
            printf("%s\n", USAGE);
            exit(0);
            break;
        }
    }

    ser_cmd_init();

    if (send_or_receive == SEND)
    {
        debug_trace("%s\n", str_to_send);
        pthread_create(&pid, NULL, ser_cmd_rcv_wrapper, NULL);

        //debug_trace("%s", str_to_send);
        //ser_cmd_send(0x02, str_to_send, strlen(str_to_send), NULL);
        while(1)
        {
            //ser_cmd_send(0x61, str_to_send, strlen(str_to_send), NULL);
            _send_10();
            sleep(2);
        }
        //ser_cmd_send(0x7f, str_to_send, strlen(str_to_send), NULL);
        //pthread_join(pid, NULL);
    }
    else if (send_or_receive == RECEIVE)
    {
        ser_cmd_rcv();
    }

    return 0;
}


