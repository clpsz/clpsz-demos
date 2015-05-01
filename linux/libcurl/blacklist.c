#include <stdio.h>
#include <string.h>
#include <curl/curl.h>


size_t curl_callback_buf_len = 0;
size_t write_callback(char *ptr, size_t size, size_t nmemb, void *userdata)
{
    size_t cpy_size;

    cpy_size = size*nmemb<curl_callback_buf_len ? size*nmemb : curl_callback_buf_len-1;
    memcpy(userdata, ptr, cpy_size);
    *((char *)userdata + cpy_size) = '\0';
    
    return cpy_size;
}

char *get_dev_id()
{
    return "RJ201410162001";
}

size_t write_callback(char *ptr, size_t size, size_t nmemb, void *userdata);

#define FINAL_HOST          "box.uhappybox.com:81"
#define UPDATE_BLACK_LIST   FINAL_HOST "/uApi.php"
static char *_get_blacklist_post_str()
{
    static char post_str[256];

    snprintf(post_str, 256, "{\"request\":\"3GBlackList\",\"device\":\"%s\",\"param\":[]}", get_dev_id());

    return post_str;
}

void get_blacklist(char buf[], size_t buf_len)
{
    CURL *easy_handle = curl_easy_init();
    CURLcode code;
    struct curl_slist *header_list=NULL;

    curl_callback_buf_len = buf_len; 
    header_list = curl_slist_append(header_list, "Content-Type: application/json");
    
    curl_easy_setopt(easy_handle, CURLOPT_HTTPHEADER, header_list);
    curl_easy_setopt(easy_handle, CURLOPT_URL, UPDATE_BLACK_LIST);
    curl_easy_setopt(easy_handle, CURLOPT_COPYPOSTFIELDS, _get_blacklist_post_str());
    curl_easy_setopt(easy_handle, CURLOPT_WRITEFUNCTION, write_callback);
    curl_easy_setopt(easy_handle, CURLOPT_WRITEDATA, buf);
    
    code = curl_easy_perform(easy_handle);

    
    curl_slist_free_all(header_list);
    curl_easy_cleanup(easy_handle);
}

