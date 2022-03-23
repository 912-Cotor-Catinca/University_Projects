#pragma once
#include "Service.h"
#include "Ethonologist.h"
#include <QtWidgets/QMainWindow>
#include "ui_EthnographicPark.h"
#include <qtableview.h>
#include "MyModel.h"
#include <qlayout.h>
#include <qlabel>
#include <qpushbutton.h>
#include <qlineedit.h>

class EthnographicPark : public QMainWindow
{
    Q_OBJECT

public:
    EthnographicPark(Service& s, Ethonologist eth ,QWidget *parent = Q_NULLPTR);

private:
    Service& serv;
    Ethonologist eth;
    QTableView* tb;
    MyModel* model;
    QLabel* descLB, * idLB, * coordLb;
    QLabel* desLB, * coodLb, *idULB, *areaLB;
    QPushButton* addBTN, * updateBTN;
    QLineEdit* descLE, * idLE, * coordLE;
    QLineEdit* desLE, * coodLE, * idULE, * areaLE;
    Ui::EthnographicParkClass ui;
    void init();

private slots:
    void add();
    void update();
};
