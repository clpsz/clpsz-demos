CFLAGS = -Wall
LDFLAGS = -lpthread


srcs = main.c
objs = $(srcs:.c=.o)

.PHONY: all
all: $(objs)
	$(CC) $(LDFLAGS) -o _main $^


$(objs):%.o:%.c
	$(CC) $(CFLAGS) -c -o $@ $<

clean:
	rm -f _main *.o

