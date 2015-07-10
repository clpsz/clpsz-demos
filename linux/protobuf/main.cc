int write();
int read();


int main(int argc, char* argv[])
{
#ifdef WRITE
    write();
#else
    read();
#endif
}
