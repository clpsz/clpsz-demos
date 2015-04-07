#include <linux/module.h>    // included for all kernel modules
#include "hello2.h"

void printk_in_hello2(void)
{
    printk(KERN_INFO "Calling printk in hello2.c\n");
}

