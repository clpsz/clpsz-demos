#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "mytcpserver.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

public slots:
    //void connect(int ip_addr);
private slots:
    void on_send_btn_clicked();

    void on_listen_btn_clicked();

private:
    Ui::MainWindow *ui;
    MyTcpServer *server;
};

#endif // MAINWINDOW_H
