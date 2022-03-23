/********************************************************************************
** Form generated from reading UI file 'EthnographicPark.ui'
**
** Created by: Qt User Interface Compiler version 5.14.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_ETHNOGRAPHICPARK_H
#define UI_ETHNOGRAPHICPARK_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_EthnographicParkClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *EthnographicParkClass)
    {
        if (EthnographicParkClass->objectName().isEmpty())
            EthnographicParkClass->setObjectName(QString::fromUtf8("EthnographicParkClass"));
        EthnographicParkClass->resize(600, 400);
        menuBar = new QMenuBar(EthnographicParkClass);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        EthnographicParkClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(EthnographicParkClass);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        EthnographicParkClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(EthnographicParkClass);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        EthnographicParkClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(EthnographicParkClass);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        EthnographicParkClass->setStatusBar(statusBar);

        retranslateUi(EthnographicParkClass);

        QMetaObject::connectSlotsByName(EthnographicParkClass);
    } // setupUi

    void retranslateUi(QMainWindow *EthnographicParkClass)
    {
        EthnographicParkClass->setWindowTitle(QCoreApplication::translate("EthnographicParkClass", "EthnographicPark", nullptr));
    } // retranslateUi

};

namespace Ui {
    class EthnographicParkClass: public Ui_EthnographicParkClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_ETHNOGRAPHICPARK_H
