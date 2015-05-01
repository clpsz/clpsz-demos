#ifndef TEST_H
#define TEST_H

#include <QObject>
#include "sender1.h"
#include "recver.h"

class test: public QObject
{
public:
    test(): s(), r()
    {
        QObject::connect(&s, SIGNAL(signal1()), &r, SLOT(slot1()));
    }

    void send_signal1()
    {
        s.send_signal1();
    }

private:
    sender1 s;
    recver r;
};

#endif // TEST_H
