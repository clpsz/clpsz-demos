#include <stdio.h>



#define _DEBUG(...) printf(__VA_ARGS__)



void __dump(void *_buf, int len)
{
    int i;
    unsigned char *buf = (unsigned char *)_buf;
    _DEBUG("************************************************\n");
    for(i = 0; i < len; i++)
    {
        _DEBUG("%02X ", buf[i]);
        if(i % 8 == 7)
        {
            _DEBUG(" ");
        }
        if(i % 16 == 15)
        {
            _DEBUG("\n");
        }
    }
    _DEBUG("\n");                                                                                                                       
}

