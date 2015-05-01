#include <stdio.h>
#include "list.h"





/*
#define LIST_HEAD_INIT(name) { &(name), &(name) }

#define LIST_HEAD(name) \
    struct list_head name = LIST_HEAD_INIT(name)

#define INIT_LIST_HEAD(ptr) do { \
    (ptr)->next = (ptr); (ptr)->prev = (ptr); \
} while (0)
*/



typedef struct _my_list
{
    int index;
    struct list_head list;
} MY_LIST;


int main()
{
    LIST_HEAD(head);
    LIST_HEAD(head1);
    MY_LIST my_list_array[10];
    MY_LIST my_list_array1[10];
    MY_LIST *p_tmp_entry;

    int i = 0;

    for (i = 0; i < 10; i++)
    {
        my_list_array[i].index = i;
        my_list_array1[i].index = i;
        INIT_LIST_HEAD(&my_list_array[i].list);
        INIT_LIST_HEAD(&my_list_array1[i].list);
        list_add(&my_list_array[i].list, &head);
        list_add_tail(&my_list_array1[i].list, &head1);
    }

    p_tmp_entry = list_entry(&my_list_array[3].list, MY_LIST, list);
    printf("index of list entry 3 is %d\n", p_tmp_entry->index);

    printf("\n\n");

    list_for_each_entry(p_tmp_entry, &head, list)
    {
        printf("index is %d\n", p_tmp_entry->index);
    }
    printf("\n\n");

    list_for_each_entry(p_tmp_entry, &head1, list)
    {
        printf("index is %d\n", p_tmp_entry->index);
    }

    return 0;
}

