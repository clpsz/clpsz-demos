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


.PHONY:modules modules_install clean

FORCE: 

else

obj-m :=netlink_kernel.o

endif

.PHONY: FROCE
user: FORCE
	gcc -Wall -g -o user netlink_user.c debug.c -lpthread

ins:
	@if ! lsmod | grep netlink_kernel > /dev/null 2>&1; then \
	echo insert netlink module; \
	insmod netlink_kernel.ko; \
	fi

rmm:
	@if lsmod | grep netlink_kernel > /dev/null 2>&1; then \
	echo remove netlink module; \
	rmmod netlink_kernel; \
	fi

