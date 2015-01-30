#include <string.h>

#include "debug.h"

static void test_level()
{
    LEVEL_DEBUG(DEBUG_INFO, "%d %s\n", DEBUG_INFO, "DEBUG_INFO");
    debug_info("%d %s\n", DEBUG_INFO, "DEBUG_INFO");
    LEVEL_DEBUG(DEBUG_WARN, "%d %s\n", DEBUG_WARN, "DEBUG_WARN");
    debug_warn("%d %s\n", DEBUG_WARN, "DEBUG_WARN");
}

static void test_hexdump()
{
    char buf[128];

    memset(buf, 0, 128);

    buf[0] = 'Z';
    buf[125] = 'P';
    buf[126] = 'P';

    HEX_DUMP(buf, 127);
}

static void test_daemon()
{
    DAEMON_PRINT("%d %s\n", 33, "kkk");
}
 

int main()
{
    test_level();
    test_hexdump();
    test_daemon();

    return 0;
}



