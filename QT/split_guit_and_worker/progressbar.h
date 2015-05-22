#ifndef FORM_H
#define FORM_H

#include <QWidget>
#include <QTimer>

namespace Ui {
class Form;
}

class ProgressBar : public QWidget
{
    Q_OBJECT

public:
    explicit ProgressBar(QWidget *parent = 0);
    ~ProgressBar();

public slots:
    void update();
    void closeSelf();

private:
    void setValue(int value);

signals:
    void progressFinished();

private:
    Ui::Form *ui;
    int currentValue;
    int totalSeconds;
    QTimer *timer;
};

#endif // FORM_H
