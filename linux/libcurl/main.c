#include <stdio.h>
#include <string.h>
#include <curl/curl.h>
#include "blacklist.h"


#define CB_BUF_SIZE 1024


int main()
{
    char buf[CB_BUF_SIZE];
    get_blacklist(buf, CB_BUF_SIZE);
    printf("%s\n", buf);

    return 0;
}

