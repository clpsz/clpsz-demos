#include "progressbar.h"
#include "ui_form.h"

ProgressBar::ProgressBar(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Form),
    currentValue(0),
    totalSeconds(5)
{
    ui->setupUi(this);
    timer = new QTimer;
    int totalMicroSeconds = totalSeconds * 1000;
    int microInterval = totalMicroSeconds / 100;
    timer->setInterval(microInterval);
    connect(timer, SIGNAL(timeout()), this, SLOT(update()));
    timer->start();
}

ProgressBar::~ProgressBar()
{
    delete ui;
}

void ProgressBar::update()
{
    setValue(currentValue++);
    if (currentValue >= 100)
    {
        emit progressFinished();
    }
}

void ProgressBar::closeSelf()
{
    close();
}

void ProgressBar::setValue(int value)
{
    ui->progressBar->setValue(value);
}
