/********************************************************************\
 * This program is free software; you can redistribute it and/or    *
 * modify it under the terms of the GNU General Public License as   *
 * published by the Free Software Foundation; either version 2 of   *
 * the License, or (at your option) any later version.              *
 *                                                                  *
 * This program is distributed in the hope that it will be useful,  *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of   *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the    *
 * GNU General Public License for more details.                     *
 *                                                                  *
 * You should have received a copy of the GNU General Public License*
 * along with this program; if not, contact:                        *
 *                                                                  *
 * Free Software Foundation           Voice:  +1-617-542-5942       *
 * 59 Temple Place - Suite 330        Fax:    +1-617-542-2652       *
 * Boston, MA  02111-1307,  USA       gnu@gnu.org                   *
 *                                                                  *
\********************************************************************/


#include <linux/module.h>
#include <linux/moduleparam.h>
#include <linux/kernel.h>
#include <linux/skbuff.h>
#include <linux/ip.h>
#include <linux/tcp.h>
#include <linux/list.h>
#include <linux/netlink.h>
#include <net/tcp.h>
#include <net/udp.h>
#include <linux/netfilter.h>
#include <linux/netfilter_ipv4.h>
#include <net/sock.h>
#include <linux/init.h>
#include <linux/types.h>
#include <linux/netdevice.h>
#include <linux/in.h>
#include <linux/netlink.h>
#include <linux/spinlock.h>
#include <linux/inetdevice.h>
#include <linux/version.h>

#define _DEBUG(fmt, args...) printk(KERN_EMERG fmt, ##args)



static int count = 0;


//unsigned int hook_out(unsigned int hooknum,
unsigned int hook_out(const struct nf_hook_ops *ops,
    struct sk_buff *skb,
    const struct net_device *in,
    const struct net_device *out,
    int (*okfn)(struct sk_buff *))
{
    if (count++ < 1000)
    {
        _DEBUG("get a packet index: %d\n", count);
    }

    return NF_ACCEPT;
}



/* A netfilter instance to use */
static struct nf_hook_ops nfho = {
    .hook = hook_out,
    .pf = PF_INET,
    .hooknum = NF_INET_PRE_ROUTING,
    .priority = NF_IP_PRI_MANGLE,
    .owner = THIS_MODULE,
};

static int __init sknf_init(void)
{
    if (nf_register_hook(&nfho)) {
        _DEBUG("nf_register_hook() failed\n");
        return -1;
    }

    _DEBUG("installed succeed\n");
    return 0;
}

static void __exit sknf_exit(void)
{
    nf_unregister_hook(&nfho);

    _DEBUG("netfilter module removed\n");
}

module_init(sknf_init);
module_exit(sknf_exit);
MODULE_AUTHOR("clpsz");
MODULE_LICENSE("GPL");

