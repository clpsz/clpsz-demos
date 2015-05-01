#include <linux/module.h>    // included for all kernel modules
#include <linux/kernel.h>    // included for KERN_INFO
#include <linux/init.h>      // included for __init and __exit macros
#include "hello2.h"

void printk_in_hello1(void)
{
    printk(KERN_INFO "Calling printk in hello1.c\n");
}

