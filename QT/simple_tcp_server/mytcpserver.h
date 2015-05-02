#ifndef MYTCPSERVER
#define MYTCPSERVER

#include <QDebug>
#include <QObject>
#include <QTcpSocket>
#include <QTcpServer>


class MyTcpServer : public QObject
{
    Q_OBJECT
public:
    MyTcpServer();

signals:
public slots:
    void newConnection();

private:
    QTcpServer *server;
};

#endif // MYTCPSERVER
