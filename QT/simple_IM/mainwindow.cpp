#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->ip_input->setText("127.0.0.1");
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_send_btn_clicked()
{
    ui->friends->setText("太后");
}
