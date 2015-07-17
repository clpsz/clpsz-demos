#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/time.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <termios.h>
#include <errno.h>
#include <pthread.h>


#include "ser_cmd.h"
#include "ser_hal.h"
#include "debug.h"


#define WRITE_DEV   "/dev/console" // write dev
#define READ_DEV    "/dev/console" // read dev


uint_8 replied = 0;
PKT *replied_pkt = NULL;


extern UART g_uartx;


static struct termios init_options;
static int serial_fd;



static int init_serial(char *dev_name, struct termios *init_options)
{
    int fd;
    struct termios options;
    
    fd = open(dev_name, O_RDWR | O_NOCTTY | O_NDELAY);
    if (fd < 0) {
        perror("open");
        return -1;
    }


    tcgetattr(fd, &options);
    memcpy(init_options, &options, sizeof(struct termios));

    options.c_cflag |= (CLOCAL | CREAD);    //set terminal status
    options.c_cflag &= ~CSIZE;              //symbol lenght
    options.c_cflag &= ~CRTSCTS;            // no hadrware flow control
    options.c_cflag |= CS8;                 //8 bit data length
    options.c_cflag &= ~CSTOPB;             //1 bit stop
    options.c_iflag |= IGNPAR;              //no odd-even check
    options.c_oflag = 0;                    //output mode
    options.c_lflag = 1;                    //don't activate terminal

    // deal with special control character
    options.c_iflag &= ~(BRKINT | ICRNL | INPCK | ISTRIP | IXON);
    options.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG);
    
    cfsetospeed(&options, B9600);//baud rate

    // set termianl attributes
    tcflush(fd, TCIFLUSH);
    tcsetattr(fd, TCSANOW, &options);

    return fd;
}



void *ser_hal_malloc(unsigned int size)
{
    return malloc(size);
}

void ser_hal_free(void *ptr)
{
    free(ptr);
}


// uart0 - write
// uart1 - read
void ser_hal_init()
{
    const char *dev = g_uartx == 0 ? WRITE_DEV : READ_DEV;
    
    serial_fd = init_serial((char *)dev, &init_options);
}

void ser_hal_write(void *buf, unsigned int len)
{
    int fd = serial_fd;
    int write_len = 0;
    write_len = write(fd, buf, len);
    //debug_trace("write len is %d\n", write_len);
    if(write_len == len) {
        return;
    } else {
        tcflush(fd, TCOFLUSH);
        return;
    }
}


int ser_hal_read(void *buf, unsigned int count)
{
    fd_set rdfds;
    int fd = serial_fd;
    int i, tmp;
    
    FD_ZERO(&rdfds);
    FD_SET(fd, &rdfds);
    for (i = 0; i < count; i++)
    {
        tmp = select(fd + 1, &rdfds, NULL, NULL, NULL);
        if (tmp <= 0)
        {
            perror("select()");
        }
        tmp = read(fd, buf + i, 1);
        if (tmp <= 0)
        {
            perror("read()");
            i--;
        }
    }
    
    return count;
}

// TODO: add semaphore
err_code ser_hal_tellsend(PKT *pkt)
{
    //debug_trace("got a reply\n");
    //_dump(pkt, pkt->mac_hdr.len);
    replied_pkt = pkt;
    replied = 1;
    return err_ok;
}


// TODO: add timeout
// timeout: xxxx ms
err_code ser_hal_rcvreply(PKT** pkt, uint_8 sn, uint_32 timeout)
{
    while (!replied)
    {
        usleep(100*1000); // 100ms
    }
    replied = 0;
    _dump(replied_pkt, replied_pkt->mac_hdr.len);
    if(pkt == NULL) //means not care reply\n
    {
        ser_hal_free(replied_pkt);
    }
    else
    {
        *pkt = replied_pkt;
    }
    return err_ok;
}



