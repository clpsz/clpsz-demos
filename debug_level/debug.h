#include <stdio.h>

// more message if debug level if bigger
#define DEBUG_OFF        0
#define DEBUG_ERROR      1
#define DEBUG_WARN       2
#define DEBUG_TRACE      3
#define DEBUG_INFO       4
#define DEBUG_LOUD       5


// set upper bound of debug message
#define G_DEBUG_LEVEL    DEBUG_WARN


#define DEBUG_RAW(...) fprintf(stderr, __VA_ARGS__)


#define LEVEL_DEBUG(level, ...) \
do { \
    if (level <= G_DEBUG_LEVEL) \
    { \
        DEBUG_RAW(__VA_ARGS__); \
    } \
} while (0)

#define debug_error(...) LEVEL_DEBUG(DEBUG_ERROR, __VA_ARGS__)
#define debug_warn(...) LEVEL_DEBUG(DEBUG_WARN, __VA_ARGS__)
#define debug_trace(...) LEVEL_DEBUG(DEBUG_TRACE, __VA_ARGS__)
#define debug_info(...) LEVEL_DEBUG(DEBUG_INFO, __VA_ARGS__)
#define debug_loud(...) LEVEL_DEBUG(DEBUG_LOUD, __VA_ARGS__)


