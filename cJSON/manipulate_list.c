#include "common.h"
#include "debug.h"
#include "manipulate_list.h"
#include "json_wrap.h"



int mk_fake_list()
{
    int i = 0;
    cJSON *root = get_json_from_file(RES_LIST_FILE);
    cJSON *fake_array = cJSON_CreateArray();
    
    cJSON *item = cJSON_GetArrayItem(root, 0);

    for (i = 0; i < 100; i++)
    {
        cJSON_AddItemToArray(fake_array, cJSON_Duplicate(item, 1));
    }

    //debug_trace("%s\n", cJSON_Print(fake_array));
    write_json_to_file(fake_array, "fake_resource_100.json");
    return 0;
}

static void prepare_dirs(const char *ios_target_dir, const char *and_target_dir)
{
    char cmd_buf[256];

    sprintf(cmd_buf, "mkdir -p %s", ios_target_dir);
    system(cmd_buf);
    sprintf(cmd_buf, "mkdir -p %s", and_target_dir);
    system(cmd_buf);

    sprintf(cmd_buf, "rm -f %s*", ios_target_dir);
    system(cmd_buf);
    sprintf(cmd_buf, "rm -f %s*", and_target_dir);
    system(cmd_buf);
}


int make_new_map(const char *list_file, const char *storage_dir, const char *ios_target_dir, const char *and_target_dir)
{
    char cmd_buf[256];
    cJSON *root = get_json_from_file(list_file);
    cJSON *item = NULL;
    cJSON *tmp = NULL;

    char *res_id, *res_type;

    int list_len = 0;
    int i;

    prepare_dirs(ios_target_dir, and_target_dir);

    if (root == NULL)
    {
        debug_error("root null\n");
        exit(1);
    }

    list_len = cJSON_GetArraySize(root);

    for (i = 0; i < list_len; i++)
    {
        item = cJSON_GetArrayItem(root, i);
        tmp = cJSON_GetObjectItem(item, "resourceId");
        res_id = print_unquote_string(tmp);
        
        tmp = cJSON_GetObjectItem(item, "type");
        res_type = print_unquote_string(tmp);
        if (strncmp(res_type, "apk", 3) == 0)
        {
            sprintf(cmd_buf, "ln -sf %s%s %s%s.apk", storage_dir, res_id, and_target_dir, res_id);
            system(cmd_buf);
            sprintf(cmd_buf, "ln -sf %s%s.icon %s%s.icon", storage_dir, res_id, and_target_dir, res_id);
            //debug_trace("%s\n", cmd_buf);
            system(cmd_buf);
        }
        else if (strncmp(res_type, "ipa", 3) == 0)
        {
            sprintf(cmd_buf, "ln -sf %s%s %s%s.ipa", storage_dir, res_id, ios_target_dir, res_id);
            system(cmd_buf);
            sprintf(cmd_buf, "ln -sf %s%s.icon %s%s.icon", storage_dir, res_id, ios_target_dir, res_id);
            //debug_trace("%s\n", cmd_buf);
            system(cmd_buf);
        }
        
        free(res_id);
        free(res_type);
    }
    
    cJSON_Delete(root);

    
    return 0;
}

int simplify_category(const char *filename)
{
    cJSON *_root = get_json_from_file(filename);
    cJSON *root;
    cJSON *root_simple = cJSON_CreateObject();
    int list_size = 0, i;


    if (_root == NULL)
    {
        debug_error("error\n");
        return -1;
    }

    root = cJSON_GetObjectItem(_root, "list");
    if (root == NULL)
    {
        debug_error("error\n");
        return -1;
    }


    list_size = cJSON_GetArraySize(root);
    for (i = 0; i < list_size; i++)
    {
        cJSON *ori_item = cJSON_GetArrayItem(root, i);
        cJSON *title = cJSON_GetObjectItem(ori_item, "title");
        char *str = print_unquote_string(title);

        if (strncmp(str, GAME_UTF8, 6) == 0)
        {
            cJSON *tags = cJSON_GetObjectItem(ori_item, "tags");
            cJSON *tag = cJSON_GetArrayItem(tags, 0);
            cJSON_AddItemToObject(root_simple, "game", cJSON_Duplicate(tag, 1));
        }
        else if (strncmp(str, APP_UTF8, 6) == 0)
        {
            cJSON *tags = cJSON_GetObjectItem(ori_item, "tags");
            cJSON *tag = cJSON_GetArrayItem(tags, 0);
            cJSON_AddItemToObject(root_simple, "app", cJSON_Duplicate(tag, 1));
        }

        free(str);
    }
    write_json_to_file(root_simple, SIMPLE_CAT_FILE);

    cJSON_Delete(_root);
    cJSON_Delete(root_simple);
    
    return 0;
}

int simplify_reslist(const char *filename)
{
    cJSON *root = get_json_from_file(filename);
    cJSON *root_simple = cJSON_CreateArray();
    int list_size = 0, i;
    

    if (root == NULL)
    {
        debug_error("error\n");
        return -1;
    }

    list_size = cJSON_GetArraySize(root);
    for (i = 0; i < list_size; i++)
    {
        cJSON *new_item = cJSON_CreateObject();
        cJSON *ori_item = cJSON_GetArrayItem(root, i);

        
        cJSON *tmp = cJSON_GetObjectItem(ori_item, "type");
        cJSON_AddItemToObject(new_item, "type", cJSON_Duplicate(tmp, 1));
        
        tmp = cJSON_GetObjectItem(ori_item, "size");
        cJSON_AddItemToObject(new_item, "size", cJSON_Duplicate(tmp, 1));
        
        tmp = cJSON_GetObjectItem(ori_item, "title");
        cJSON_AddItemToObject(new_item, "title", cJSON_Duplicate(tmp, 1));
        
        tmp = cJSON_GetObjectItem(ori_item, "resourceId");
        cJSON_AddItemToObject(new_item, "resourceId", cJSON_Duplicate(tmp, 1));

        
        tmp = cJSON_GetObjectItem(ori_item, "description");
        cJSON_AddItemToObject(new_item, "description", cJSON_Duplicate(tmp, 1));

        tmp = cJSON_GetObjectItem(ori_item, "tags");
        cJSON_AddItemToObject(new_item, "tags", cJSON_Duplicate(tmp, 1));

        cJSON_AddItemToArray(root_simple, new_item);
    }

    write_json_to_file(root_simple, SIMPLE_RES_FILE);

    cJSON_Delete(root);
    cJSON_Delete(root_simple);
    return 0;
}


