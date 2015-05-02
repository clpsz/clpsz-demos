#ifndef MYTCPSERVER
#define MYTCPSERVER

#include <QDebug>
#include <QObject>
#include <QtNetwork/QTcpSocket>
#include <QtNetwork/QTcpServer>


class MyTcpServer : public QObject
{
    Q_OBJECT
public:
    MyTcpServer();
    int listen(int port);

signals:
public slots:
    void newConnection();

private:
    QTcpServer *server;
};

#endif // MYTCPSERVER
