#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <termios.h>
#include <errno.h>

#define DEVICE "/dev/console"

int serial_fd = 0;
struct termios init_options;


int init_serial(void)
{
    serial_fd = open(DEVICE, O_RDWR | O_NOCTTY | O_NDELAY);
    if (serial_fd < 0) {
        perror("open");
        return -1;
    }

    struct termios options;

    tcgetattr(serial_fd, &options);
    memcpy(&init_options, &options, sizeof(struct termios));

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
    tcflush(serial_fd, TCIFLUSH);
    tcsetattr(serial_fd, TCSANOW, &options);

    return 0;
}


int uart_send(int fd, char *data, int datalen)
{
    int len = 0;
    len = write(fd, data, datalen);
    if(len == datalen) {
        return len;
    } else {
        tcflush(fd, TCOFLUSH);
        return -1;
    }

    return 0;
}

int uart_recv(int fd, char *data, int datalen)
{
    int len=0, ret = 0;
    fd_set fs_read;
    struct timeval tv_timeout;

    FD_ZERO(&fs_read);
    FD_SET(fd, &fs_read);
    tv_timeout.tv_sec  = (10*20/115200+2);
    tv_timeout.tv_usec = 0;

    ret = select(fd+1, &fs_read, NULL, NULL, &tv_timeout);
    printf("ret = %d\n", ret);



    if (FD_ISSET(fd, &fs_read)) {
        len = read(fd, data, datalen);
        printf("len = %d\n", len);
        return len;
    } else {
        perror("select");
        return -1;
    }

    return 0;
}

int main(int argc, char **argv)
{
    init_serial();

    char buf[]="hello world";
    char buf1[10];
    uart_send(serial_fd, buf, 10);

    uart_recv(serial_fd, buf1, 10);

    tcflush(serial_fd, TCIFLUSH);
    tcsetattr(serial_fd, TCSANOW, &init_options);

    close(serial_fd);
    return 0;
}


