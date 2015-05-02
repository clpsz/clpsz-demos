#-------------------------------------------------
#
# Project created by QtCreator 2015-05-01T16:37:57
#
#-------------------------------------------------

QT       += core gui
QT       += network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = test3
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp\
        mytcpserver.cpp


HEADERS  += mainwindow.h\
        mytcpserver.h\

FORMS    += mainwindow.ui
