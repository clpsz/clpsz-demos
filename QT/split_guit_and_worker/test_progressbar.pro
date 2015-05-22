#-------------------------------------------------
#
# Project created by QtCreator 2015-05-22T10:14:03
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = test_progressbar
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    highloadthread.cpp \
    progressbar.cpp

HEADERS  += mainwindow.h \
    highloadthread.h \
    progressbar.h

FORMS    += mainwindow.ui \
    progressbar.ui
