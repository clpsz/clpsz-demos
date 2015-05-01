#include <QCoreApplication>
#include <QDebug>

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    qDebug() << "hello world";
    qDebug() << "hello clpsz";
    
    return a.exec();
}
