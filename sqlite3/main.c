#include <stdio.h>
#include <sqlite3.h>


int callback(void *arg, int cols, char **row, char **names)
{
    int i;
    
    for (i = 0; i < cols; i++)
    {
        printf("%-20s ", names[i]);
    }
    putchar('\n');
    for (i = 0; i < cols; i++)
    {
        printf("%-20s ", row[i]);
    }
    putchar('\n');
    return 0;
}


int main()
{
    sqlite3 *db;
    char buf[256];


    sqlite3_open("/tmp/tst.db", &db);

    sprintf(buf, "%s", "CREATE TABLE Students(Id integer,Name text,age integer);");
    sqlite3_exec(db, buf, NULL, NULL, NULL);
    sprintf(buf, "%s", "INSERT INTO Students VALUES(676863075, \"Peter Zuo\", \"24\");");
    sqlite3_exec(db, buf, NULL, NULL, NULL);
    sprintf(buf, "%s", "INSERT INTO Students VALUES(676863076, \"Peter Zuo\", \"25\");");
    sqlite3_exec(db, buf, NULL, NULL, NULL);
    sprintf(buf, "%s", "INSERT INTO Students VALUES(676863077, \"Peter Zuo\", \"26\");");
    sqlite3_exec(db, buf, NULL, NULL, NULL);

    sprintf(buf, "%s", "SELECT * FROM Students;");
    sqlite3_exec(db, buf, callback, NULL, NULL);

    sqlite3_close(db);
    return 0;
}

