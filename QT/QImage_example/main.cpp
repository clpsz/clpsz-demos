#include <QApplication>
#include <QLabel>
#include <QImage>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    QImage image(200, 200, QImage::Format_RGB32);
    QRgb value;

    for (int i = 0; i < 200; i++)
        for (int j = 0; j < 200; j++)
        {
            value = qRgb(255, 255, 255);
            image.setPixel(i, j, value);
        }

    for (int i = 0; i < 200; i++)
    {
        value = qRgb(255, 0, 0);
        image.setPixel(i, 100, value);
    }

    for (int i = 0; i < 200; i++)
    {
        value = qRgb(0, 255, 0);
        image.setPixel(100, i, value);
    }


    QLabel lbl;

    lbl.setPixmap(QPixmap::fromImage(image));
    lbl.setGeometry(200,200,200,200);
    lbl.show();


    return a.exec();
}

