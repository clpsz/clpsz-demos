#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include <net/sock.h>
#include <linux/socket.h>
#include <linux/net.h>
#include <asm/types.h>
#include <linux/netlink.h>
#include <linux/skbuff.h>


#define _DEBUG(fmt, args...) printk(KERN_EMERG "%s %d: "fmt, __FILE__, __LINE__, ##args)
#define NETLINK_USER        31
#define MAX_PAYLOAD         1024 /* maximum payload size*/

pid_t g_pid = -1;

struct sock *nl_sk = NULL;


static void netlink_receive(struct sk_buff *skb);
struct netlink_kernel_cfg _cfg = {
    .input = netlink_receive,
};


void nl_send_to_user(void *buf, size_t buf_len)
{
    struct sk_buff *skb;
    struct nlmsghdr *nlh;
    int len = NLMSG_SPACE(MAX_PAYLOAD);
    int ret;

    if (g_pid < 0)
    {
        return;
    }

    skb = alloc_skb(len, GFP_ATOMIC);
     if (!skb){
        _DEBUG("Net_link: allocate failed.\n");
        return;
    }
    nlh = nlmsg_put(skb, 0, 0, 0, MAX_PAYLOAD, 0);
    memset(nlh, 0, NLMSG_SPACE(MAX_PAYLOAD));

    //NETLINK_CB(skb).dst_group= 0; // unicast 

    nlh->nlmsg_len = NLMSG_SPACE(buf_len);
    nlh->nlmsg_pid = 0; // from kernel
    nlh->nlmsg_type = 0;

    memcpy(NLMSG_DATA(nlh), buf, buf_len);

    ret = netlink_unicast(nl_sk, skb, g_pid, MSG_DONTWAIT);
    if (ret < 0)
    {
        _DEBUG("Netlink unicast error");
    }
}

static void netlink_receive(struct sk_buff *skb)
{
    struct nlmsghdr *nlh;
    char *data;
    unsigned int pid;

    if (skb->len >= NLMSG_SPACE(0))
    {
        nlh = nlmsg_hdr(skb);
        pid = nlh->nlmsg_pid;
        data = NLMSG_DATA(nlh);

        g_pid = nlh->nlmsg_pid;
    }

    nl_send_to_user("Hello user, I have got your message", 100);
    nl_send_to_user("Hello user, I have got your message, tell you twitce", 100);
}


static int __init netlink_init(void)
{
    _DEBUG("Entering: %s\n", __FUNCTION__);  
    nl_sk=netlink_kernel_create(&init_net, NETLINK_USER, &_cfg);  
    if(!nl_sk)  
    {   
        _DEBUG("Error creating socket.\n");  
        return -1;  
    }  
    return 0;  
}

static void __exit netlink_exit(void)
{
    _DEBUG("Exiting %s\n", __FUNCTION__);  
    netlink_kernel_release(nl_sk);  
}

module_init(netlink_init);
module_exit(netlink_exit);
MODULE_AUTHOR("clpsz");
MODULE_LICENSE("GPL");

