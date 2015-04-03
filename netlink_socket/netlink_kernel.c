#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include <net/sock.h>
#include <linux/socket.h>
#include <linux/net.h>
#include <asm/types.h>
#include <linux/netlink.h>
#include <linux/skbuff.h>


#define _DEBUG(fmt, args...) printk(KERN_EMERG fmt, ##args)
#define NETLINK_USER 31

struct sock *nl_sk = NULL;


static void netlink_receive(struct sk_buff *skb);
struct netlink_kernel_cfg _cfg = {
    .input = netlink_receive,
};

static void netlink_receive(struct sk_buff *skb)
{
    _DEBUG("kernel receive something\n");
}


static int __init netlink_init(void)
{
    _DEBUG("Entering: %s\n", __FUNCTION__);  
    nl_sk=netlink_kernel_create(&init_net, NETLINK_USER, &_cfg);  
    if(!nl_sk)  
    {   
        _DEBUG("Error creating socket.\n");  
        return -10;  
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

