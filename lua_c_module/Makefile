all: hello_lib.c
	gcc -fPIC -shared -I/usr/include/lua5.2/ -o hello_lib.so hello_lib.c -llua5.2 

clean:
	rm -f main *.o *.so

