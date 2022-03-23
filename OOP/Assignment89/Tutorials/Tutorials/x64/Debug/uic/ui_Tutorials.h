/********************************************************************************
** Form generated from reading UI file 'Tutorials.ui'
**
** Created by: Qt User Interface Compiler version 5.14.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_TUTORIALS_H
#define UI_TUTORIALS_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_TutorialsClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *TutorialsClass)
    {
        if (TutorialsClass->objectName().isEmpty())
            TutorialsClass->setObjectName(QString::fromUtf8("TutorialsClass"));
        TutorialsClass->resize(600, 400);
        menuBar = new QMenuBar(TutorialsClass);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        TutorialsClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(TutorialsClass);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        TutorialsClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(TutorialsClass);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        TutorialsClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(TutorialsClass);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        TutorialsClass->setStatusBar(statusBar);

        retranslateUi(TutorialsClass);

        QMetaObject::connectSlotsByName(TutorialsClass);
    } // setupUi

    void retranslateUi(QMainWindow *TutorialsClass)
    {
        TutorialsClass->setWindowTitle(QCoreApplication::translate("TutorialsClass", "Tutorials", nullptr));
    } // retranslateUi

};

namespace Ui {
    class TutorialsClass: public Ui_TutorialsClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_TUTORIALS_H
