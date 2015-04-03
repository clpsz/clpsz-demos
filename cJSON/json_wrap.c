#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <getopt.h>


#include "debug.h"
#include "cJSON.h"
#include "json_wrap.h"

char* print_unquote_string(cJSON *str_obj)
{
    char *ori_str = NULL;
    char *new_str = NULL;
    size_t len = 0;
    if (str_obj->type != cJSON_String)
    {
        debug_error("not a string\n");
        return NULL;
    }

    ori_str = cJSON_PrintUnformatted(str_obj);
    len = strlen(ori_str);
    ori_str[len - 1] = '\0'; // delete last "
    new_str = (char *)malloc(len - 1);
    strncpy(new_str, ori_str+1, len - 1); // delte first "
    free(ori_str);
    
    return new_str;
}

char *get_str_from_file(const char *filename)
{
 	FILE *f=fopen(filename,"rb");
 	if (!f)
    {

        perror("Open failed: ");
                printf("%s\n", filename);
        return NULL;
 	}
  fseek(f,0,SEEK_END);
  long len=ftell(f);
  fseek(f,0,SEEK_SET);
  
	char *data=(char*)malloc(len+1);
  if (data == NULL)
  {
    return NULL;
  }
  fread(data,1,len,f);
  fclose(f);
  return data;  
}


cJSON *get_json_from_file(const char *filename)
{
  char *file_str = get_str_from_file(filename);
  cJSON *root;
  
  if (file_str  == NULL)
  {
    debug_error("error\n");
    return NULL;
  }
  root = cJSON_Parse(file_str);
  free(file_str);

  return root;
}

void write_json_to_file(cJSON *root, const char *filename)
{
    char *json_str = cJSON_Print(root);
    FILE *f;
    if (json_str != NULL)
    {
      f = fopen(filename, "w");
      if (f == NULL)
      {
        return;
      }
      fwrite(json_str, 1, strlen(json_str), f);
      fclose(f);
      free(json_str);
    }
}


