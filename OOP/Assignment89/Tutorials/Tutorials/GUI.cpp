#include "GUI.h"
#include <algorithm>
#include <qmessagebox.h>
#include <shlobj.h>
#include <shlwapi.h>
#include <objbase.h>

GUI::GUI(Service& s, ServiceUser& suser) : serv{s}, suser{suser}
{
	initGUI();
	populateList();
	SignalsAndSlots();
}

void GUI::initGUI()
{
	this->try1 = new QTableWidget{};
	this->watchlist = new QListWidget{};
	this->presenters = new QTableWidget{};
	this->presenterLE = new QLineEdit{};
	this->titleLE = new QLineEdit{};
	this->durationLE = new QLineEdit{};
	this->linkLE = new QLineEdit{};
	this->likesLE = new QLineEdit{};
	this->linksLE = new QLineEdit{};
	this->pressLE = new QLineEdit{};
	this->titLE = new QLineEdit{};
	this->durLE = new QLineEdit{};
	this->liksLE = new QLineEdit{};
	
	setButtons();

	//layout
	QHBoxLayout* mainLayout = new QHBoxLayout{this};
	QVBoxLayout* adminLayout = new QVBoxLayout{};
	QVBoxLayout* userLayout = new QVBoxLayout{};
	mainLayout->addLayout(adminLayout, 4);
	mainLayout->addLayout(userLayout, 6);
	adminLayout->stretch(4);
	// Admin Part
	QLabel* labelAdmin = new QLabel{"Administrator Mode"};
	QFont font("Times New Roman", 20, 10, true);
	labelAdmin->setAlignment(Qt::AlignCenter);
	labelAdmin->setFont(font);
	adminLayout->addWidget(labelAdmin);

	//TableWidget
	QGridLayout* table = new QGridLayout{};
	this->try1->setRowCount(this->serv.GetTutorial().size());
	QStringList horzHeaders;
	horzHeaders << "Titile" << "Presenter" << "Duration" << "Link" << "Likes";
	this->try1->setColumnCount(5);
	this->try1->setHorizontalHeaderLabels(horzHeaders);
	this->try1->setColumnWidth(0, 168);
	this->try1->setColumnWidth(3, 188);
	table->addWidget(this->try1);
	adminLayout->addLayout(table, 5);

	//Labels and lineEdits
	QFormLayout* tutorials_detals = new QFormLayout{};
	tutorials_detals->addRow("Title", this->titleLE);
	tutorials_detals->addRow("Presenter", this->presenterLE);
	tutorials_detals->addRow("Duration", this->durationLE);
	tutorials_detals->addRow("Link", this->linkLE);
	tutorials_detals->addRow("Likes", this->likesLE);
	
	adminLayout->addLayout(tutorials_detals);

	//Buttons layout
	QGridLayout* buttonsArea = new QGridLayout{};
	buttonsArea->addWidget(this->addBTN, 0, 0);
	buttonsArea->addWidget(this->deleteBTN, 0, 1);
	buttonsArea->addWidget(this->updateBTN, 0, 2);
	buttonsArea->addWidget(this->displayBTN, 0, 3);

	adminLayout->addLayout(buttonsArea);

	//User Part
	QLabel* labelUser = new QLabel{ "User Mode" };
	QFont fontt("Times New Roman", 20, 10, true);
	labelUser->setAlignment(Qt::AlignCenter);
	labelUser->setFont(fontt);
	userLayout->addWidget(labelUser);

	//ListWidget 
	QGridLayout* main = new QGridLayout{};
	QLabel* l1 = new QLabel{ "Tutorials with a given presenter" };
	QLabel* l2 = new QLabel{ "Watchlist" };
	main->addWidget(l1, 0, 0);
	main->addWidget(l2, 0, 1);
	main->addWidget(this->presenters, 1, 0);
	main->addWidget(this->watchlist, 1, 1);
	userLayout->addLayout(main);


	//Buttons HTML or CSV
	QGridLayout* fileArea = new QGridLayout{};
	fileArea->addWidget(this->HTMLbtn, 0, 0);
	fileArea->addWidget(this->CSVbtn, 0, 1);
	userLayout->addLayout(fileArea);

	QFormLayout* watchlist_detals = new QFormLayout{};
	watchlist_detals->addRow("Titile", this->titLE);
	watchlist_detals->addRow("Presenter", this->pressLE);
	watchlist_detals->addRow("Duration", this->durLE);
	watchlist_detals->addRow("Link", this->linksLE);
	watchlist_detals->addRow("Likes", this->liksLE);

	userLayout->addLayout(watchlist_detals);

	QGridLayout* buttonsLayout = new QGridLayout{};
	buttonsLayout->addWidget(this->addu, 0, 0, 1, 1);
	buttonsLayout->addWidget(this->deleteu, 0, 1, 1, 1);
	buttonsLayout->addWidget(this->open, 1, 0, 1, 2);
	buttonsLayout->addWidget(this->display, 2, 0, 1, 1);
	buttonsLayout->addWidget(this->filter, 2, 1, 1, 1);

	userLayout->addLayout(buttonsLayout);
}

void GUI::setButtons()
{
	this->addBTN = new QPushButton{ "Add" };
	this->deleteBTN = new QPushButton{ "Delete" };
	this->updateBTN = new QPushButton{ "Update" };

	this->addu = new QPushButton{ "Add" };
	this->deleteu = new QPushButton{ "Delete" };
	this->display = new QPushButton{ "Open in App" };
	this->CSVbtn = new QPushButton{ "CSV" };
	this->HTMLbtn = new QPushButton{ "HTML" };
	this->filter = new QPushButton{ "Filter by Presenters" };
	this->open = new QPushButton{ "See Tutorial online" };
	this->addBTN->setStyleSheet("QPushButton{font-size: 18px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
	//this->displayBTN->setStyleSheet("QPushButton{font-size: 14px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
	this->deleteBTN->setStyleSheet("QPushButton{font-size: 18px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
	this->updateBTN->setStyleSheet("QPushButton{font-size: 18px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
	this->addu->setStyleSheet("QPushButton{font-size: 18px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
	this->deleteu->setStyleSheet("QPushButton{font-size: 18px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
	this->display->setStyleSheet("QPushButton{font-size: 18px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
	this->CSVbtn->setStyleSheet("QPushButton{font-size: 18px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
	this->HTMLbtn->setStyleSheet("QPushButton{font-size: 18px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
	this->filter->setStyleSheet("QPushButton{font-size: 18px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
	this->open->setStyleSheet("QPushButton{font-size: 18px;font-family: Times New Roman;color: rgb(255, 255, 255);background-color: rgb(38,56,76);}");
}

void GUI::populatewatchlist()
{
	this->watchlist->clear();
	std::vector<MasterC> tutorials = this->suser.GetWatchList();
	int i = 1;
	int j = 0;
	for (MasterC& c : tutorials)
	{
		QFont font("Times New Roman", 10, 10, false);
		this->watchlist->setFont(font);
		this->watchlist->addItem(QString::fromStdString(c.getTitle() + " - " + c.getPresenter() + " - " +
			c.getDuration() + " - " + c.getLink() + " - " + to_string(c.getLikes())));
	}
}


void GUI::populateList()
{
	this->try1->clear();
	std::vector<MasterC> tutorials = this->serv.GetTutorial();
	int i = 1;
	int j = 0;
	for (MasterC& t : tutorials)
	{
		QFont font("Times New Roman", 10, 10, false);
		this->try1->setFont(font);
		
		QString st = QString::fromStdString(t.getTitle());
		QString st1 = QString::fromStdString(t.getPresenter());
		QString st2 = QString::fromStdString(t.getDuration());
		QString st3 = QString::fromStdString(t.getLink());
		QString st4 = QString::fromStdString(to_string(t.getLikes()));
		QTableWidgetItem* s = new QTableWidgetItem{st};
		QTableWidgetItem* s1 = new QTableWidgetItem{st1};
		QTableWidgetItem* s2 = new QTableWidgetItem{st2};
		QTableWidgetItem* s3 = new QTableWidgetItem{st3};
		QTableWidgetItem* s4 = new QTableWidgetItem{st4};
		this->try1->setItem(i-1, j, s);
		this->try1->setItem(i-1, j+1, s1);
		this->try1->setItem(i-1, j+2, s2);
		this->try1->setItem(i-1, j+3, s3);
		this->try1->setItem(i-1, j+4, s4);
		i++;
		this->try1->setAlternatingRowColors(true);
		this->try1->setStyleSheet("alternate-background-color:rgb(213, 217, 222);background-color:rgb(230, 232, 235)");
	}
		
}

void GUI::SignalsAndSlots()
{
	QObject::connect(this->try1, &QTableWidget::itemSelectionChanged, [this]() {
		int index = this->getIndex(this->try1);
		if (index < 0)
			return;
		MasterC c = this->serv.GetTutorial()[index];
		this->titleLE->setText(QString::fromStdString(c.getTitle()));
		this->presenterLE->setText(QString::fromStdString(c.getPresenter()));
		this->durationLE->setText(QString::fromStdString(c.getDuration()));
		this->linkLE->setText(QString::fromStdString(c.getLink()));
		this->likesLE->setText(QString::fromStdString(to_string(c.getLikes())));
		});

	QObject::connect(this->watchlist, &QListWidget::itemSelectionChanged, [this]() {
		int index = this->getIndex1();
		MasterC c = this->suser.GetWatchList()[index];
		this->titLE->setText(QString::fromStdString(c.getTitle()));
		this->pressLE->setText(QString::fromStdString(c.getPresenter()));
		this->durLE->setText(QString::fromStdString(c.getDuration()));
		this->linksLE->setText(QString::fromStdString(c.getLink()));
		this->liksLE->setText(QString::fromStdString(to_string(c.getLikes())));
		});

	QObject::connect(this->presenters, &QTableWidget::itemSelectionChanged, [this]() {
		int index = this->getIndex(this->presenters);
		if (index < 0)
			return;
		MasterC c = this->serv.GetPresenters(this->pressLE->text().toStdString())[index];
		this->titLE->setText(QString::fromStdString(c.getTitle()));
		this->pressLE->setText(QString::fromStdString(c.getPresenter()));
		this->durLE->setText(QString::fromStdString(c.getDuration()));
		this->linksLE->setText(QString::fromStdString(c.getLink()));
		this->liksLE->setText(QString::fromStdString(to_string(c.getLikes())));
		});

	QObject::connect(this->addBTN, &QPushButton::clicked, this, &GUI::addTutorial);
	QObject::connect(this->deleteBTN, &QPushButton::clicked, this, &GUI::deleteTutorial);
	QObject::connect(this->updateBTN, &QPushButton::clicked, this, &GUI::updateTutorial);
	QObject::connect(this->CSVbtn, &QPushButton::clicked, this, &GUI::CSVlistdisplay);
	QObject::connect(this->HTMLbtn, &QPushButton::clicked, this, &GUI::HTMLlistdisplay);
	QObject::connect(this->display, &QPushButton::clicked, this, &GUI::openinAPP);
	QObject::connect(this->deleteu, &QPushButton::clicked, this, &GUI::deleteWatchlist);
	QObject::connect(this->filter, &QPushButton::clicked, this, &GUI::filterlist);
	QObject::connect(this->addu, &QPushButton::clicked, this, &GUI::addWatchlist);
	QObject::connect(this->open, &QPushButton::clicked, this, &GUI::openLink);
}

int GUI::getIndex(QTableWidget* t) const
{
	QModelIndexList selectedIndexes = t->selectionModel()->selectedIndexes();
	if (selectedIndexes.size() == 0)
	{
		this->titleLE->clear();
		this->presenterLE->clear();
		this->durationLE->clear();
		this->linkLE->clear();
		this->likesLE->clear();
		return -1;
	}
	int selectedIndex = selectedIndexes.at(0).row();
	return selectedIndex;
}

void GUI::addTutorial()
{
	string title = this->titleLE->text().toStdString();
	string presenter = this->presenterLE->text().toStdString();
	string duration = this->durationLE->text().toStdString();
	string link = this->linkLE->text().toStdString();
	string likes = this->likesLE->text().toStdString();
	int like = stoi(likes);

	try {
		this->serv.add(title, presenter, duration, link, like);
		int last = this->serv.GetTutorial().size() - 1;
		this->try1->insertRow(last);
		for (int i = 0; i < 5; ++i)
			this->try1->setCurrentCell(last, i);

		this->populateList();
	}
	catch (ValidationException& ex)
	{
		QMessageBox::warning(this, "Exception", QString::fromStdString(ex.getMessage()));
	}
	catch (Exception& ex) {
		QMessageBox::warning(this, "Exception", "Another tutorial already has this link!");
	}
}

void GUI::deleteTutorial()
{
	int index = this->getIndex(this->try1);
	if (index < 0)
	{
		QMessageBox::critical(this, "Errors", "No tutorial was selected");
		return;
	}

	MasterC c = this->serv.GetTutorial()[index];
	this->serv.remove_srv(c.getLink());
	
	this->try1->removeRow(this->try1->currentRow());
	
	this->populateList();
}

void GUI::updateTutorial()
{
	int index = this->getIndex(this->try1);
	if (index < 0)
	{
		QMessageBox::critical(this, "Errors", "No tutorial was selected");
		return;
	}
	MasterC c = this->serv.GetTutorial()[index];
	string title = this->titleLE->text().toStdString();
	string presenter = this->presenterLE->text().toStdString();
	string duration = this->durationLE->text().toStdString();
	string likes = this->likesLE->text().toStdString();
	this->serv.update_srv(title, presenter, duration, c.getLink(), stoi(likes));

	this->populateList();
}

void GUI::CSVlistdisplay()
{
	this->watchlist->clear();
	this->suser.setRepo("csv");
	std::vector<MasterC> tutorials = this->suser.GetWatchList();
	for (MasterC& c : tutorials)
	{
		QFont font("Times New Roman", 10, 10, false);
		this->watchlist->setFont(font);
		this->watchlist->addItem(QString::fromStdString(c.getTitle() + " - " + c.getPresenter() + " - " +
			c.getDuration() + " - " + c.getLink() + " - " + to_string(c.getLikes())));
	}

}

void GUI::HTMLlistdisplay()
{
	this->watchlist->clear();
	this->suser.setRepo("html");
	std::vector<MasterC> tutorials = this->suser.GetWatchList();
	for (MasterC& c : tutorials)
	{
		QFont font("Times New Roman", 10, 10, false);
		this->watchlist->setFont(font);
		this->watchlist->addItem(QString::fromStdString(c.getTitle() + " - " + c.getPresenter() + " - " +
			c.getDuration() + " - " + c.getLink() + " - " + to_string(c.getLikes())));
	}
}

void GUI::openinAPP()
{
	this->suser.getRepoUser()->write();
	this->suser.getRepoUser()->open();
}

void GUI::deleteWatchlist()
{
	int index = this->getIndex1();
	if (index < 0)
	{
		QMessageBox::critical(this, "Errors", "No tutorial was selected");
		return;
	}

	MasterC c = this->suser.GetWatchList()[index];
	QMessageBox::StandardButton reply = QMessageBox::question(this, "Question", "Did you enjoy watching this video? Give it a like", QMessageBox::Yes | QMessageBox::No);
	if (reply == QMessageBox::Yes)
	{
		this->serv.update_likes(c.getLink());
		this->populateList();
	}
	
	this->suser.remove(c.getLink());
	this->suser.getRepoUser()->write();
	this->populatewatchlist();
}

void GUI::addWatchlist()
{
	int index = this->getIndex(this->presenters);
	if (index < 0)
	{
		QMessageBox::critical(this, "Errors", "No tutorial was selected");
		return;
	}
	MasterC c = this->serv.GetPresenters(this->pressLE->text().toStdString())[index];
	QMessageBox::StandardButton reply = QMessageBox::question(this, "Question", "Would you like to see this tutorial?", QMessageBox::Yes | QMessageBox::No);

	if (reply == QMessageBox::Yes)
	{
		string link = c.getLink();
		wstring s_t = wstring(link.begin(), link.end());
		LPCWSTR s1 = TEXT("open");
		LPCWSTR s2 = s_t.c_str();
		ShellExecute(NULL, s1, s2, NULL, NULL, SW_SHOWNORMAL);
	}
	
	
	this->suser.add(c);
	
	this->populatewatchlist();
	this->suser.getRepoUser()->write();
}

void GUI::filterlist()
{
	this->presenters->clear();
	string presenter = this->pressLE->text().toStdString();
	std::vector<MasterC> presenters = this->serv.GetPresenters(presenter);
	this->presenters->setRowCount(presenters.size());
	QStringList horzHeaders;
	horzHeaders << "Titile" << "Presenter" << "Duration" << "Link" << "Likes";
	this->presenters->setColumnCount(5);
	this->presenters->setHorizontalHeaderLabels(horzHeaders);
	int i{ 1 }, j{ 0 };
	for (MasterC& t : presenters)
	{
		QFont font("Times New Roman", 10, 10, false);
		this->presenters->setFont(font);

		QString st = QString::fromStdString(t.getTitle());
		QString st1 = QString::fromStdString(t.getPresenter());
		QString st2 = QString::fromStdString(t.getDuration());
		QString st3 = QString::fromStdString(t.getLink());
		QString st4 = QString::fromStdString(to_string(t.getLikes()));
		QTableWidgetItem* s = new QTableWidgetItem{ st };
		QTableWidgetItem* s1 = new QTableWidgetItem{ st1 };
		QTableWidgetItem* s2 = new QTableWidgetItem{ st2 };
		QTableWidgetItem* s3 = new QTableWidgetItem{ st3 };
		QTableWidgetItem* s4 = new QTableWidgetItem{ st4 };
		this->presenters->setItem(i - 1, j, s);
		this->presenters->setItem(i - 1, j + 1, s1);
		this->presenters->setItem(i - 1, j + 2, s2);
		this->presenters->setItem(i - 1, j + 3, s3);
		this->presenters->setItem(i - 1, j + 4, s4);
		i++;
		this->presenters->setAlternatingRowColors(true);
		this->presenters->setStyleSheet("alternate-background-color:rgb(213, 217, 222);background-color:rgb(230, 232, 235)");
	}
}

int GUI::getIndex1() const
{
	QModelIndexList selectedIndexes = this->watchlist->selectionModel()->selectedIndexes();
	if (selectedIndexes.size() == 0)
	{
		this->titleLE->clear();
		this->presenterLE->clear();
		this->durationLE->clear();
		this->linkLE->clear();
		this->likesLE->clear();
		return -1;
	}
	int selectedIndex = selectedIndexes.at(0).row();
	return selectedIndex;
}

void GUI::openLink()
{
	int index = this->getIndex1();
	if (index < 0)
	{
		QMessageBox::critical(this, "Errors", "No tutorial was selected");
		return;
	}

	MasterC c = this->suser.GetWatchList()[index];

	string link = c.getLink();
	wstring s_t = wstring(link.begin(), link.end());
	LPCWSTR s1 = TEXT("open");
	LPCWSTR s2 = s_t.c_str();
	ShellExecute(NULL, s1, s2, NULL, NULL, SW_SHOWNORMAL);
}


