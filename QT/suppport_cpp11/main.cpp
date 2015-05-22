#include <QCoreApplication>
#include <QDebug>

int fint()
{
    return 3;
}

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    decltype(fint()) b = 33;

    qDebug() << b;


    return a.exec();
}
