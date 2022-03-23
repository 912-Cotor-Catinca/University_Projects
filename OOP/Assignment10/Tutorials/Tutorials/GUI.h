#pragma once
#include "MyModel.h"
#include <qwidget.h>
#include "Service.h"
#include "CommandManager.h"
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
#include <qshortcut.h>
#include <qtableview.h>
class GUI :
    public QWidget
{
private:
    Service& serv;
    ServiceUser& suser;
    CommandManager* cmd;
    // graphical elements
    QTableWidget* try1, *presenters;
    //QListWidget* watchlist;
    QTableView* watchlist;
    MyModel* watchlistModel;
    QLineEdit* titleLE, * presenterLE, * durationLE, * linkLE, * likesLE, *pressLE, *linksLE, *titLE, *durLE, *liksLE;
    QPushButton* addBTN, *deleteBTN, *updateBTN, *addu, *deleteu, *display, *CSVbtn, *HTMLbtn, *filter, *open, *undoBTN, *redoBTN;
    QShortcut* ctrl_z, * ctrl_r;

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
    void Undo();
    void Redo();
};

