#include <QCoreApplication>
#include <vector>
#include <algorithm>
#include <QDebug>

void demoAuto()
{
    int a = 1;
    int b = 2;
    auto c = a + b;

    qDebug() << c;
}


int fint()
{
    return 3;
}

void demoDecltype()
{
    decltype(fint()) b = 33;
    qDebug() << b;
}

void demoNewFor()
{
    std::vector<int> myVector;
    myVector.push_back(3);
    myVector.push_back(2);
    myVector.push_back(1);

    // QT style
    foreach(int item, myVector)
    {
        qDebug() << item;
    }

    // C++11 style
    for(int item : myVector)
    {
        qDebug() << item;
    }
}

void demoLambda()
{
    int x = 4;
    auto f = [x](int y)->int{return x + y + 3;};

    qDebug() << f(5);

    std::vector<int> myVector;
    myVector.push_back(2);
    myVector.push_back(1);
    myVector.push_back(3);

    // simple sort
    std::sort(myVector.begin(), myVector.end());
    for (int item : myVector)
    {
        qDebug() << item;
    }

    // using lambda
    std::sort(myVector.begin(), myVector.end(), [](const int &a, const int &b)->bool{return a > b;});
    for (int item : myVector)
    {
        qDebug() << item;
    }
}

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    std::vector<void (*)(void)> demos;
    demos.push_back(demoAuto);
    demos.push_back(demoDecltype);
    demos.push_back(demoNewFor);
    demos.push_back(demoLambda);

    for (void (*demo)() : demos)
    {
        demo();
        qDebug() << "";
    }

    return a.exec();
}
