KDIR := /lib/modules/$(shell uname -r)/build
PWD := $(shell pwd)

modules:
	$(MAKE) -C $(KDIR) M=$(PWD) modules

modules_install:
	$(MAKE) -C $(KDIR) M=$(PWD) modules_install

clean:
	$(MAKE) -C $(KDIR) M=$(PWD) clean
	rm -f user

.PHONY:modules modules_install clean

FORCE: 

obj-m :=netlink_kernel.o

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

