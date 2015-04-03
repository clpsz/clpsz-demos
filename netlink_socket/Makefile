ifeq ($(KERNELRELEASE),)

KDIR := /lib/modules/$(shell uname -r)/build
PWD := $(shell pwd)

modules:
	$(MAKE) -C $(KDIR) M=$(PWD) modules

modules_install:
	$(MAKE) -C $(KDIR) M=$(PWD) modules_install

clean:
	rm -rf *.o *.ko .depend .*.cmd *.mod.o *.mod.c \
	Module.* modules.* .tmp_versions user

user: FORCE
	gcc -o user netlink_user.c debug.c

.PHONY:modules modules_install clean FORCE

FORCE: 

else

obj-m :=netlink_kernel.o

endif

