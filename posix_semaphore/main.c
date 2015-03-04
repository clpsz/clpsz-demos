#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>

sem_t sem;

void *thread(void *dummy)
{
    printf("new thread\n");
    sem_post(&sem);

    return NULL;
}

int main(int argc, char *argv[])
{
    pthread_t pid;
    // not share within process, initial value 0
    pthread_create(&pid, NULL, thread, NULL);
    sem_init(&sem, 0, 0);
    sem_wait(&sem);
    printf("main_thread\n");
    sem_post(&sem);

    pthread_join(pid, NULL);

    return 0;
}

