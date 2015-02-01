#include <stdio.h>
#include <unistd.h>
#include <getopt.h>

int opt(int argc, char *argv[]);
int opt_long(int argc, char *argv[]);




int main (int argc, char *argv[])
{
    return opt_long(argc, argv);
}


//./_main -a -cfoo -c bar
int opt(int argc, char *argv[])
{
    int opt;
    while ((opt = getopt(argc, argv, "abc:d:")) != -1)
    {
        switch (opt)
        {
        case 'a':
            printf("%c\n", opt);
            break;
        case 'b':
            printf("%c\n", opt);
            break;
        case 'c':
            printf("%c, %s\n", opt, optarg);
            break;
        case 'd':
            printf("%c, %s\n", opt, optarg);
            break;
        case '?':
            break;
        default:
            printf("default %d\n", opt);
            break;
        }
    }
}


/*
struct option {
    const char *name;
    int         has_arg;
    int        *flag;
    int         val;
};
*/

static struct option long_options[] = {
    {"foo",     required_argument, 0,  0 },
    {"bar",     no_argument,       0,  0 },
    {"apple",   no_argument,       0, 'a'},
    {"create",  required_argument, 0, 'c'},
    {0,         0,                 0,  0 }
};


//./_main -a -b -c foo -cfoo --foo=foo --bar --apple --create foo
int opt_long(int argc, char *argv[])
{
    int opt, long_index;

    while ((opt = getopt_long(argc, argv, "abc:d:", long_options, &long_index)) != -1)
    {
        switch (opt)
        {
        case 0:
            printf("name: %s, value: %s\n", long_options[long_index].name, optarg);
            break;
        case 'a':
            printf("%c\n", opt);
            break;
        case 'b':
            printf("%c\n", opt);
            break;
        case 'c':
            printf("%c, %s\n", opt, optarg);
            break;
        case 'd':
            printf("%c, %s\n", opt, optarg);
            break;
        case '?':
            break;
        default:
            printf("default %d\n", opt);
            break;
        }
    }

    return 0;
}


