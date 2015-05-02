#include <QCoreApplication>
#include <QDebug>
#include "mytcpserver.h"
#include "mytcpserver2.h"


int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    MyTcpServer server;
    //MyTcpServer2 server;

    return a.exec();
}
