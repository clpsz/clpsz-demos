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

    socket->write("hello clpsz");
    socket->flush();
    socket->waitForBytesWritten(3000);
    socket->close();
}
