#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <linux/netlink.h>
#include "debug.h"


#define NETLINK_USER 31
#define MAX_PAYLOAD 1024 /* maximum payload size*/



int send_message()
{
    int sock_fd;
    int ret;
    struct sockaddr_nl src_addr;
    struct sockaddr_nl dst_addr;
    struct nlmsghdr *nlh = NULL;

    // create sock
    sock_fd=socket(PF_NETLINK, SOCK_RAW, NETLINK_USER);  
    if(sock_fd < 0)  
    {
        perror("netlink socket():");
        return -1;  
    }

    // set and bind to src address
    memset(&src_addr, 0, sizeof(src_addr));
    src_addr.nl_family = AF_NETLINK;
    src_addr.nl_pid = getpid();
    ret = bind(sock_fd, (struct sockaddr*)&src_addr, sizeof(src_addr));  
    if (ret < 0)
    {
        perror("netlink bind():");
        goto err2;
    }

    // set dest address
    memset(&dst_addr, 0, sizeof(dst_addr));  
    dst_addr.nl_family = AF_NETLINK;  
    dst_addr.nl_pid = 0;    /* For Linux Kernel */  
    dst_addr.nl_groups = 0; /* unicast */  

    // prepare message
    nlh = (struct nlmsghdr *)malloc(NLMSG_SPACE(MAX_PAYLOAD));  
    if (nlh == NULL)
    {
        perror("malloc:");
        goto err1;
    }
    memset(nlh, 0, NLMSG_SPACE(MAX_PAYLOAD));  
    nlh->nlmsg_len = NLMSG_SPACE(MAX_PAYLOAD);  
    nlh->nlmsg_pid = getpid();  
    nlh->nlmsg_flags = 0;  



    ret = sendto(sock_fd, &nlh, nlh->nlmsg_len, 0, (struct sockaddr*)&dst_addr, sizeof(struct sockaddr_nl));
    if (ret < 0)
    {
        perror("netlink sendto():");
        return -1;
    }

    free(nlh);
    close(sock_fd);
    return 0;

err1:
    free(nlh);
err2:
    close(sock_fd);
    return -1;
}

int main()
{
    send_message();

    return 0;
}

