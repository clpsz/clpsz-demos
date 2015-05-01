#ifndef RECEIVER_H
#define RECEIVER_H

#include <QObject>
#include <QDebug>

class recver: public QObject
{
    Q_OBJECT
public:
    recver()
    {
    }

public slots:
    void slot1()
    {
        qDebug() << "got slot1";
    }
};

#endif // RECEIVER_H
