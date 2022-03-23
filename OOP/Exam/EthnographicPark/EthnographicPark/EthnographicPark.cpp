#include "EthnographicPark.h"
#include <qmessagebox.h>

EthnographicPark::EthnographicPark(Service& s, Ethonologist eth, QWidget *parent)
    : serv{ s }, eth{ eth }, QMainWindow(parent)
{
    ui.setupUi(this);
    
    QWidget::setWindowTitle(eth.getName().c_str());
    init();
    connect(this->addBTN, &QPushButton::clicked, this, &EthnographicPark::add);
    connect(this->updateBTN, &QPushButton::clicked, this, &EthnographicPark::update);
}

void EthnographicPark::init()
{
    QWidget* qwg = new QWidget(this);
    QVBoxLayout* mainlayout = new QVBoxLayout{ this };
    this->tb = new QTableView{};
    this->model = new MyModel{ serv, eth };
    this->addBTN = new QPushButton{ "Add" };
    this->updateBTN = new QPushButton{ "Update" };
    this->descLB = new QLabel{ "Description: " };
    this->desLB = new QLabel{ "Description: " };
    this->idLB = new QLabel{ "Id: " };
    this->idULB = new QLabel{ "Id: " };
    this->coordLb = new QLabel{ "Coordinates: " };
    this->coodLb = new QLabel{ "Coordinates: " };
    this->areaLB = new QLabel{ "Area: " };
    this->descLE = new QLineEdit{};
    this->desLE = new QLineEdit{};
    this->idLE = new QLineEdit{};
    this->idULE = new QLineEdit{};
    this->coordLE = new QLineEdit{};
    this->coodLE = new QLineEdit{};
    this->areaLE = new QLineEdit{};
    this->tb->setModel(model);
    mainlayout->addWidget(this->tb);

    QGridLayout* grid = new QGridLayout{};
    grid->addWidget(this->idLB, 0, 0);
    grid->addWidget(this->idLE, 0, 1);
    grid->addWidget(this->descLB, 0, 2);
    grid->addWidget(this->descLE, 0, 3);
    grid->addWidget(this->coordLb, 0, 4);
    grid->addWidget(this->coordLE, 0, 5);
    grid->addWidget(this->addBTN, 0, 6);
    mainlayout->addLayout(grid);
    QGridLayout* grid1 = new QGridLayout{};
    grid1->addWidget(this->idULB, 0, 0);
    grid1->addWidget(this->idULE, 0, 1);
    grid1->addWidget(this->desLB, 0, 2);
    grid1->addWidget(this->desLE, 0, 3);
    grid1->addWidget(this->areaLB, 0, 4);
    grid1->addWidget(this->areaLE, 0, 5);
    grid1->addWidget(this->coodLb, 0, 6);
    grid1->addWidget(this->coodLE, 0, 7);
    grid1->addWidget(this->updateBTN, 0, 8);
    mainlayout->addLayout(grid1);
    qwg->setLayout(mainlayout);
    setCentralWidget(qwg);
}

void EthnographicPark::update()
{
    string area = this->areaLE->text().toStdString();
    int id = stoi(this->idULE->text().toStdString());
    vector<Buildings> b = this->serv.getBuildingsSorted(this->eth.getArea());
    if (area != this->eth.getArea())
    {
        QMessageBox::critical(this, "Errors", "This is not your area");
        return;
    }
    string description = this->desLE->text().toStdString();
    string coor = this->coodLE->text().toStdString();
    string del = ";";
    int pos = 0;
    string token;
    vector<string> c;
    while ((pos = coor.find(del)) != string::npos)
    {
        token = coor.substr(0, pos);
        c.push_back(token);
        coor.erase(0, pos + del.length());
    }
    try {
        this->serv.update(id, description, area, c);
    }
    catch (exception& ex)
    {
        QMessageBox::warning(this, "Exception", ex.what());
    }
}


void EthnographicPark::add() {
    int id = stoi(this->idLE->text().toStdString());
    string desc = this->descLE->text().toStdString();
    string coord = this->coordLE->text().toStdString();
    string del = ";";
    int pos = 0;
    string token;
    vector<string> c;
    while ((pos = coord.find(del)) != string::npos)
    {
        token = coord.substr(0, pos);
        c.push_back(token);
        coord.erase(0, pos + del.length());
    }
    try {
        this->serv.add(id, desc, this->eth.getArea(), c);
    }
    catch (exception& ex)
    {
        QMessageBox::warning(this, "Exception", ex.what());
    }
}
