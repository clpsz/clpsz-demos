#include <QCoreApplication>
#include "test.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    test tt;
    tt.send_signal1();

    return a.exec();
}
