#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_Tutorials.h"

class Tutorials : public QMainWindow
{
    Q_OBJECT

public:
    Tutorials(QWidget *parent = Q_NULLPTR);

private:
    Ui::TutorialsClass ui;
};
