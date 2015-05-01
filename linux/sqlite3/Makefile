CFLAGS = -Wall
LDFLAGS = -lsqlite3

target = _main
srcs = main.c
objs = $(srcs:.c=.o)

.PHONY: all
all: $(target)

$(target): $(objs)
	$(CC) $(LDFLAGS) -o _main $^

$(objs):%.o:%.c
	$(CC) $(CFLAGS) -c -o $@ $<

clean:
	rm -f _main *.o

