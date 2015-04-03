#ifndef __JSON_WRAP_H
#define __JSON_WRAP_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <getopt.h>

#include "cJSON.h"


cJSON *get_json_from_file(const char *filename);
char *get_str_from_file(const char *filename);
void write_json_to_file(cJSON *root, const char *filename);
char* print_unquote_string(cJSON *str_obj);

#endif

