CC = gcc -g
CFLAGS = -Wall
LDFLAGS = -lm -g

target = mal
srcs = main.c cJSON.c debug.c json_wrap.c manipulate_list.c
objs = $(srcs:.c=.o)
headers = $(wildcard *.h)


.PHONY: all 
all: $(target)

$(target): $(objs) $(headers) FORCE
	$(CC) -o $(target) $(objs) $(LDFLAGS)
	@rm -f *.o

$(objs):%.o:%.c
	@$(CC) $(CFLAGS) -c -o $@ $<

.PHONY: FORCE
FORCE: 

clean:
	rm -f $(target) *.o

