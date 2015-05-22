#include <QDebug>
#include "highloadthread.h"

HighLoadThread::HighLoadThread()
{

}

HighLoadThread::~HighLoadThread()
{

}

void HighLoadThread::run()
{
    int count = 5;
    while (count--)
    {
        qDebug() << "Running";
        QThread::sleep(1);
    }

    emit workFinished();
}

