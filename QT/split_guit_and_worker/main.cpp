#include "mainwindow.h"
#include "progressbar.h"
#include <QApplication>
#include <QThread>
#include "highloadthread.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;
    w.show();

    ProgressBar *f = new ProgressBar;
    f->show();
    //f.setWindowFlags(Qt::WA_DeleteOnClose);
    f->setAttribute(Qt::WA_DeleteOnClose);


    HighLoadThread h;
    QObject::connect(&h, SIGNAL(workFinished()), f, SLOT(closeSelf()));
    h.start();

    return a.exec();
}
