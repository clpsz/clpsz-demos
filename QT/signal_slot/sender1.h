#ifndef SENDER_H
#define SENDER_H

#include <QObject>

class sender1: public QObject
{
    Q_OBJECT
public:
    sender1()
    {
    }

    void send_signal1()
    {
        emit signal1();
    }

signals:
    void signal1();
};

#endif // SENDER_H
