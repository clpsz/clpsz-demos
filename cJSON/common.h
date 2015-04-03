#ifndef __COMMON_H
#define __COMMON_H

#define TEST

#ifndef TEST
#define RES_ROOT_DIR        "/data/me.youlebao/resource/"
#else
#define RES_ROOT_DIR        "./"
#endif
#define RES_STORAGE_DIR     RES_ROOT_DIR "storage/"

#define RES_LIST_FILE       RES_ROOT_DIR "flashbot.resource"
#define RES_CATEGORY_FILE   RES_ROOT_DIR "flashbot.list"
#define SIMPLE_RES_FILE     RES_ROOT_DIR "flashbot.res_simple"
#define SIMPLE_CAT_FILE     RES_ROOT_DIR "flashbot.list_simple"

#ifndef TEST
#define LINK_ROOT_DIR       "/www/"
#else
#define LINK_ROOT_DIR       "/tmp/"
#endif
#define IOS_LINK_TARGET_DIR LINK_ROOT_DIR "iphone/download/"
#define AND_LINK_TARGET_DIR LINK_ROOT_DIR "andriod/download/"

#define APP_UTF8            "\xE5\xBA\x94\xE7\x94\xA8"
#define GAME_UTF8           "\xE6\xB8\xB8\xE6\x88\x8F"

#endif
