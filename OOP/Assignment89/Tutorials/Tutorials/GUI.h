#pragma once
#include <qwidget.h>
#include "Service.h"
#include "ServiceUser.h"
#include <qlistwidget.h>
#include <qpushbutton.h>
#include <qlineedit.h>
#include <qlayout.h>
#include <qformlayout.h>
#include <qgridlayout.h>
#include <qlabel.h>
#include <qfont.h>
#include <qmainwindow.h>
#include <qtablewidget.h>
class GUI :
    public QWidget
{
private:
    Service& serv;
    ServiceUser& suser;
    // graphical elements
    QTableWidget* try1, *presenters;
    QListWidget* watchlist;
    QLineEdit* titleLE, * presenterLE, * durationLE, * linkLE, * likesLE, *pressLE, *linksLE, *titLE, *durLE, *liksLE;
    QPushButton* addBTN, *deleteBTN, *updateBTN, *addu, *deleteu, *display, *displayBTN, *CSVbtn, *HTMLbtn, *filter, *open;

public:
    GUI(Service& s, ServiceUser& suer);
private:
    void initGUI();
    void setButtons();
    void populatewatchlist();
    void populateList();
    void SignalsAndSlots();
    int getIndex(QTableWidget * t)const;
    void addTutorial();
    void deleteTutorial();
    void updateTutorial();
    void CSVlistdisplay();
    void HTMLlistdisplay();
    void openinAPP();
    void deleteWatchlist();
    void addWatchlist();
    void filterlist();
    int getIndex1()const;
    void openLink();
};

