#include "mytcpserver.h"


MyTcpServer::MyTcpServer()
{
    server = new QTcpServer();
    if(!server->listen(QHostAddress::Any, 6666))
    {
        qDebug()<<server->errorString();
    }

    QObject::connect(server, SIGNAL(newConnection()), this, SLOT(newConnection()));
}

void MyTcpServer::newConnection()
{
    QTcpSocket *socket = server->nextPendingConnection();

    socket->write("hello client");
    socket->flush();
    socket->waitForBytesWritten(3000);
    socket->close();
}

int MyTcpServer::listen(int port)
{
    server = new QTcpServer();
    if(!server->listen(QHostAddress::Any, port))
    {
        qDebug()<<server->errorString();
        return -1;
    }

    QObject::connect(server, SIGNAL(newConnection()), this, SLOT(newConnection()));
    return 0;
}
