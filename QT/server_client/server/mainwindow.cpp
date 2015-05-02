#include <QDebug>
#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "mytcpserver.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    server = new MyTcpServer();
}

MainWindow::~MainWindow()
{
    delete server;
    delete ui;
}

void MainWindow::on_send_btn_clicked()
{
    ui->friends->setText("太后");
}

void MainWindow::on_listen_btn_clicked()
{
    QString s_port_num = ui->listen_port_input->text();
    int port_num = s_port_num.toInt();
    server->listen(port_num);
}
