#ifndef HIGHLOADTHREAD_H
#define HIGHLOADTHREAD_H

#include <QtCore>
#include <QObject>
#include <QThread>


class HighLoadThread : public QThread
{
    Q_OBJECT
public:
    HighLoadThread();
    ~HighLoadThread();

signals:
    void workFinished();

private:
    void run();
};

#endif // HIGHLOADTHREAD_H
