#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <getopt.h>

#include "common.h"
#include "debug.h"
#include "cJSON.h"
#include "json_wrap.h"
#include "manipulate_list.h"






int main (int argc, char *argv[])
{
    mk_fake_list();
    simplify_reslist(RES_LIST_FILE);
    simplify_category(RES_CATEGORY_FILE);

    return 0;
}



