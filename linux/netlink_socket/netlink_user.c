#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <linux/netlink.h>
#include "debug.h"


#define NETLINK_USER        31
#define MAX_PAYLOAD         1024 /* maximum payload size*/


typedef struct
{
    struct nlmsghdr hdr;
    char buf[MAX_PAYLOAD];
}NL_MSG;

int sock_fd;
struct sockaddr_nl src_addr;
struct sockaddr_nl dest_addr;


//struct sockaddr_nl {
//        __kernel_sa_family_t    nl_family;      /* AF_NETLINK   */
//        unsigned short  nl_pad;         /* zero         */
//        __u32           nl_pid;         /* port ID      */
//        __u32           nl_groups;      /* multicast groups mask */
//};


//struct nlmsghdr {
//        __u32           nlmsg_len;      /* Length of message including header */
//        __u16           nlmsg_type;     /* Message content */
//        __u16           nlmsg_flags;    /* Additional flags */
//        __u32           nlmsg_seq;      /* Sequence number */
//        __u32           nlmsg_pid;      /* Sending process port ID */
//};



int netlink_init_socket(struct sockaddr_nl *src_addr)
{
    int sock_fd;
    int ret;

    sock_fd = socket(PF_NETLINK, SOCK_RAW, NETLINK_USER);  
    if (sock_fd < 0)
    {
        perror("netlink socket():");
        exit(-1);
    }

    memset(src_addr, 0, sizeof(struct sockaddr_nl));
    src_addr->nl_family = AF_NETLINK;
    src_addr->nl_pid = getpid();
    ret = bind(sock_fd, (struct sockaddr*)src_addr, sizeof(struct sockaddr_nl));  
    if (ret < 0)
    {
        perror("netlink bind():");
        exit(-1);
    }

    return sock_fd;
}

void netlink_init_dest_addr(struct sockaddr_nl *dest_addr)
{
    memset(dest_addr, 0, sizeof(struct sockaddr_nl));
    dest_addr->nl_family = AF_NETLINK;  
    dest_addr->nl_pid = 0;    /* For Linux Kernel */  
    dest_addr->nl_groups = 0; /* unicast */  
}


int real_send(int sock_fd, struct sockaddr_nl *src_addr, struct sockaddr_nl *dest_addr, void *buf, size_t buf_len)
{
    NL_MSG msg_buf;
    struct nlmsghdr *nlh = (struct nlmsghdr *)&msg_buf;
    int addr_len = sizeof(struct sockaddr_nl);
    int ret;

    nlh->nlmsg_len = NLMSG_SPACE(buf_len);  
    nlh->nlmsg_pid = getpid();
    nlh->nlmsg_type = 0; // define your types
    memcpy(NLMSG_DATA(nlh), buf, buf_len);

    ret = sendto(sock_fd, (char *)&msg_buf, NLMSG_SPACE(buf_len), 0, (struct sockaddr*)dest_addr, addr_len);

    return ret;
}

int send_message()
{
    int ret;


    ret = real_send(sock_fd, &src_addr, &dest_addr, "hello xxx", 100);
    if (ret < 0)
    {
        perror("netlink sendto():");
        return -1;
    }

    return 0;
}

void *recv_thread(void *dummy)
{
    NL_MSG msg_buf;
    struct nlmsghdr *nlh = (struct nlmsghdr*)&msg_buf;
    socklen_t addr_len = sizeof(struct sockaddr_nl);
    char *data;
    int ret;
    
    while (1)
    {
        ret = recvfrom(sock_fd, (char *)&msg_buf, sizeof(msg_buf), 0, (struct sockaddr*)&dest_addr, &addr_len);
        if (ret < 0)
        {
            perror("Netlink recvfrom():");
            exit(-1);
        }
        nlh = (struct nlmsghdr *)&msg_buf;
        data = NLMSG_DATA(nlh);
        debug_info("%s\n", data);
    }

    return NULL;
}


int main()
{
    pthread_t tid;
    int ret;

    sock_fd = netlink_init_socket(&src_addr);
    netlink_init_dest_addr(&dest_addr);

    pthread_create(&tid, 0, recv_thread, NULL);

    ret = real_send(sock_fd, &src_addr, &dest_addr, "hello xxx", 100);
    if (ret < 0)
    {
        perror("netlink sendto():");
        return -1;
    }

    pthread_join(tid, NULL);

    return 0;
}

