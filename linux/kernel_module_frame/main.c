#include <linux/module.h>    // included for all kernel modules
#include <linux/kernel.h>    // included for KERN_INFO
#include <linux/init.h>      // included for __init and __exit macros
#include "hello1.h"
#include "hello2.h"

MODULE_LICENSE("GPL");
MODULE_AUTHOR("clpsz");
MODULE_DESCRIPTION("A Simple Hello World module");

int __init hello_init(void)
{
    printk(KERN_INFO "Hello world!\n");
    printk_in_hello1();
    printk_in_hello2();

    return 0;    // Non-zero return means that the module couldn't be loaded.
}

void __exit hello_cleanup(void)
{
    printk(KERN_INFO "Cleaning up module.\n");
}

module_init(hello_init);
module_exit(hello_cleanup);
