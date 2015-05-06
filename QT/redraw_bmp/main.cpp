#include <QApplication>
#include <QLabel>
#include <QImage>
#include <QFile>
#include <QDebug>


typedef struct _BMPHEAD
{
    qint32 size;
    qint32 reserved;
    qint32 offset;
} BMPHEAD;

typedef struct _BMPINFOHEAD
{
    qint32 infoSize;
    qint32 width;
    qint32 heidght;
    qint16 plane;
    qint16 bitPerPix;
    qint32 compressed;
    qint32 dataSize;
    qint32 vertReso;
    qint32 horiReso;
    qint32 numOfColors;
    qint32 numOfImportanColors;
}BMPINFOHEAD;



int main(int argc, char *argv[])
{
    QApplication a(argc, argv);


    QFile file(":/images/cat");

    qint16 magic;
    BMPHEAD bh;
    BMPINFOHEAD bih;

    file.open(QIODevice::ReadOnly);
    file.read((char *)&magic, sizeof(magic));
    file.read((char *)&bh, sizeof(bh));
    file.read((char *)&bih, sizeof(bih));

    qint32 width = bih.width;
    qint32 height = bih.heidght;


    qint32 wbytes = bih.width*3;
    qint32 paddingBytes = wbytes + (4-(wbytes%4));


    QImage image(width, height, QImage::Format_RGB32);
    QRgb value;


    int bytesPerLine = paddingBytes;
    quint8 buf[2048];

    for (int i = 0; i < height; i++)
    {
        int ret = file.read((char *)buf, bytesPerLine);
        if (ret <= 0)
        {
            qDebug() << "read error";
            return -1;
        }

        for (int j = 0; j < width; j++)
        {
            quint8 red = buf[j*3 + 2];
            quint8 green = buf[j*3 + 1];
            quint8 blue = buf[j*3];

            value = qRgb(red, green, blue);
            image.setPixel(j, height-i-1, value);
        }
    }

    QLabel lbl;

    lbl.setPixmap(QPixmap::fromImage(image));
    lbl.setGeometry(200,200,width,height);

    lbl.show();


    return a.exec();
}
