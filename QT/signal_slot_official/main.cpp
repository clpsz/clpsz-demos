#include <QCoreApplication>
#include "Counter.h"

int main(int argc, char *argv[])
{
    QCoreApplication app(argc, argv);
    Counter a, b;

    // when value of a changed, also set b's value
    QObject::connect(&a, &Counter::valueChanged, &b, &Counter::setValue);

    a.setValue(12);     // a.value() == 12, b.value() == 12
    a.show(); b.show();

    b.setValue(48);     // a.value() == 12, b.value() == 48
    a.show(); b.show();

    return app.exec();
}
