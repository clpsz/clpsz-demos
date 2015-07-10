CC = g++
CFLAGS = -Wall -I/home/zpp/bin/include/
LDFLAGS = -L/home/zpp/bin/slib/ -lprotobuf

ifdef WRITE
CFLAGS += -DWRITE
endif


target = _main
srcs = main.cc lm.hello.pb.cc read.cc write.cc
objs = $(srcs:.cc=.o)
#headers = $(wildcard *.h)


.PHONY: all
all: $(target)

$(target): $(objs) $(headers) FORCE
	$(CC) -o $@ $(objs) $(LDFLAGS)
	@-rm -f *.o

$(objs):%.o:%.cc
	$(CC) $(CFLAGS) -c -o $@ $<

.PHONY: FORCE
FORCE: 

clean:
	rm -f $(target) *.o

