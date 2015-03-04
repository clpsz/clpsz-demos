#include <stdio.h>
#include <unistd.h>
#include "daemonize.h"


int main(int argc, char *argv[])
{
    daemonize("aaaaa");

    pause();

    return 0;
}

