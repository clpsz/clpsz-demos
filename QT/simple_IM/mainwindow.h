#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>

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

private:
    Ui::MainWindow *ui;
};

#endif // MAINWINDOW_H
