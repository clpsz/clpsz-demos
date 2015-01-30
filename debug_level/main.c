#include "debug.h"

int main()
{
    LEVEL_DEBUG(DEBUG_INFO, "%d %s\n", DEBUG_INFO, "DEBUG_INFO");
    debug_info("%d %s\n", DEBUG_INFO, "DEBUG_INFO");
    LEVEL_DEBUG(DEBUG_WARN, "%d %s\n", DEBUG_WARN, "DEBUG_WARN");
    debug_warn("%d %s\n", DEBUG_WARN, "DEBUG_WARN");

    return 0;
}



