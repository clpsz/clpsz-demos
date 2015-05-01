#ifndef COUNTER_H
#define COUNTER_H

#include <QObject>
#include <QDebug>

class Counter : public QObject
{
    Q_OBJECT

public:
    Counter() { m_value = 0; }

    int value() const { return m_value; }
    void show() const { qDebug() << "value is: " << m_value; }


public slots:
    void setValue(int value);

signals:
    void valueChanged(int newValue);

private:
    int m_value;
};

#endif // COUNTER_H
