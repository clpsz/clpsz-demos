ifeq ($(KERNELRELEASE),)

KDIR := /lib/modules/$(shell uname -r)/build
PWD := $(shell pwd)

modules:
	$(MAKE) -C $(KDIR) M=$(PWD) modules

modules_install:
	$(MAKE) -C $(KDIR) M=$(PWD) modules_install

clean:
	rm -rf *.o *.ko .depend .*.cmd *.mod.o *.mod.c \
	Module.* modules.* .tmp_versions

.PHONY:modules modules_install clean

else

obj-m :=netfilter.o

endif

