CFLAGS += -Wall
LDFLAGS += -L. -lm -lpthread

target = uart_comm
srcs = main.c ser_cmd.c ser_hal.c ser_handler.c debug.c
objs = $(srcs:.c=.o)
headers = $(wildcard *.h)


.PHONY: all
all: $(target)

$(target): $(objs) FORCE
	$(CC) -o $@ $(objs) $(LDFLAGS) 
	@echo +++++++++++++++++++build good++++++++++++++++++

$(objs):%.o:%.c
	@$(CC) $(CFLAGS) -c -o $@ $^

.PHONY: FORCE
FORCE: 

clean:
	rm -f $(target) *.o

