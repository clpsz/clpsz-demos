#include <stdio.h>

typedef union _endian{
    unsigned int num;
    unsigned char array[4];
} ENDIAN;

int main (int argc, char *argv[])
{
    ENDIAN e = {0x11223344}; 
    
    if (e.array[0] == 0x11)
    {
        printf("Big endian.\n");
    }
    else if (e.array[0] == 0x44)
    {
        printf("Little endian.\n");
    }
    else
    {
        printf("Unknown, first byte is %02x.\n", e.array[0]);
    }

    return 0;
}

