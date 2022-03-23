#include "UI.h"
#include <iostream>
#include <stdio.h>

#include <shlobj.h>
#include <shlwapi.h>
#include <objbase.h>
using namespace std;

UI::UI()
{
	this->s = new Service;
	this->s_user = new ServiceUser;
}

void UI::add()
{
	string title, presenter, duration, link;
	int likes;
	cout << "Title: ";
	getline(cin, title);
	getline(cin, title);
	cout << "Presenter: ";
	getline(cin, presenter);
	cout << "Duration: ";
	getline(cin, duration);
	cout << "Link: ";
	getline(cin, link);
	cout << "Likes: ";
	cin >> likes;
	string token1 = duration.substr(0, duration.find(":"));
	string token2 = duration.substr(duration.find(":") + 1, duration.length());

	int hours = stoi(token1);
	int minute = stoi(token2);
	if (likes > 0 && hours < 25 && hours > 0 && minute < 61 && minute > 0)
	{
		int a = this->s->add(title, presenter, duration, link, likes);
		if (a != 1)
			cout << "The tutorial already exist" << "\n";
	}
	else
		cout << "Wrong input" << "\n";
}

void UI::remove()
{
	std::string link;
	cout << "The link: ";
	getline(cin, link);
	getline(cin, link);
	int a = this->s->remove_srv(link);
	if (a == -1)
		cout << "The given link does not exist." << "\n";
}

void UI::update()
{
	string title, presenter, duration, link;
	int likes;

	cout << "Link: ";
	getline(cin, link);
	getline(cin, link);
	cout << "Title: ";
	
	getline(cin, title);
	cout << "Presenter: ";
	getline(cin, presenter);
	cout << "Duration: ";
	getline(cin, duration);
	
	cout << "Likes: ";
	cin >> likes;
	string token1 = duration.substr(0, duration.find(":"));
	string token2 = duration.substr(duration.find(":")+1, duration.length());
	
	int hours = stoi(token1);
	int minute = stoi(token2);
	if (likes > 0 && hours < 25 && hours > 0 && minute < 61 && minute > 0)
	{
		int a = this->s->update_srv(title, presenter, duration, link, likes);
		if (a == -1)
			cout << "The tutorial with given link does not exist" << "\n";
	}	
	else
		cout << "Wrong input" << "\n";
}

void UI::display()
{
	for (int i = 0; i < this->getService()->getRepo()->getDynArray()->getLength(); ++i)
		cout << this->getService()->getRepo()->getDynArray()->getElems()[i].toString();
}

void UI::menu_admin()
{
	cout << "Press 1 to add a tutorial" << "\n";
	cout << "Press 2 to delete a tutorial" << "\n";
	cout << "Press 3 to update a tutorial" << "\n";
	cout << "Press 4 to display all tutorials" << "\n";
	cout << "Press 5 to close the aplication" << "\n";
}

void UI::start()
{
	bool done = false;
	while (!done)
	{
		string mode;
		cout << "Choose the mode: 1 for Administration, 2 for User, 3 to exit: ";
		cin >> mode;
		int nr = stoi(mode);
		if (nr == 1)
		{
			menu_admin();
			this->start_admin();
		}
		if (nr == 2)
		{
			this->menu_user();
			this->start_user();
		}
		if (nr == 3)
		{
			done = true;
		}

	}
}

void UI::display_watchlist()
{
	if (this->s_user->getRepoUser()->getDynArray()->getLength() == 0)
		cout << "The watch list is empty" << "\n";
	for (int i = 0; i < this->s_user->getRepoUser()->getDynArray()->getLength(); ++i)
		cout << this->s_user->getRepoUser()->getDynArray()->getElems()[i].toString();
}

void UI::delete_watchlist()
{
	std::string link;
	cout << "The link: ";
	getline(cin, link);
	getline(cin, link);
	int likes = this->s_user->getRepoUser()->getDynArray()->getElems()[this->s_user->getRepoUser()->search_by_link(link)].getLikes();
	bool a = this->s_user->remove(link);
	if (a == false)
		cout << "The tutorial is not in the watch list" << "\n";
	cout << "If you liked the tutorial, give it a like! Like it? yes or no >> " << "\n";
	string answ;
	cin >> answ;
	if (answ == "yes")
	{	
		likes++;
		this->s->getRepo()->getDynArray()->getElems()[this->s->getRepo()->search_by_link(link)].setLikes(likes);
	}
}

void UI::add_watchlist()
{
	string presenter;
	DynArray<MasterC>* pres;
	bool f = false;
	while (!f)
	{
		cout << "The name of the presenter: ";
		getline(cin, presenter);
		getline(cin, presenter);
		if (this->getService()->getRepo()->search_by_pres(presenter) != -1 || presenter.length() == 0)
			f = true;
		else
			cout << "The given presenter does not exists" << "\n";
	}
	
	if (presenter.length() == 0)
		pres = this->getService()->getRepo()->getDynArray();
	else
		pres = this->s->presenters(presenter);
	bool done = false;
	while (!done)
	{
		for (int i = 0; i < pres->getLength(); ++i)
		{
			cout << pres->getElems()[i].toString();
			string link = pres->getElems()[i].getLink();
			wstring s_t = wstring(link.begin(), link.end());
			LPCWSTR s1 = TEXT("open");
			LPCWSTR s2 = s_t.c_str();
			ShellExecute(NULL, s1, s2, NULL, NULL, SW_SHOWNORMAL);
			string answ;
			cout << "Do you like the tutorial? If yes add in the watchlist! yes or no >> ";
			cin >> answ;
			if (answ == "yes")
			{
				int a = this->s_user->add(pres->getElems()[i]);
				if (a != 1)
					cout << "The tutorial has been already added! " << "\n";
			}
			string answ2;
			cout << "Continue with tutorials? yes or no >> ";
			cin >> answ2;
			if (answ2 == "yes")
			{
				if (i == pres->getLength() - 1)
					i = -1;
				else continue;
			}
			if (answ2 == "no")
			{
				i = pres->getLength() - 1;
				done = true;
			}
		}
	}
}

void UI::start_user()
{
	bool done = false;
	while (!done)
	{
		char cmd[15];
		cout << "Enter a command >> ";
		cin >> cmd;
		int nr = atoi(cmd);
		if (nr == 1)
		{
			this->add_watchlist();
		}
		if(nr == 2)
		{
			this->delete_watchlist();
		}
		if (nr == 3)
		{
			this->display_watchlist();
		}
		if (nr == 4)
		{
			cout << "Goodbye dear user!" << "\n";
			done = true;
		}
		if (nr == 0)
		{
			cout << "Wrong input!" << "\n";
		}
	}
}

void UI::start_admin()
{
	bool done = false;
	while (!done)
	{
		char cmd[15];
		cout << "Enter a command: ";
		cin >> cmd;
		int nr = atoi(cmd);
		if (nr == 1)
		{
			this->add();
		}
		if (nr == 2)
		{
			this->remove();
		}
		if (nr == 3)
		{
			this->update();
		}
		if (nr == 4)
		{
			this->display();
		}
		if (nr == 5)
		{
			cout << "Byee" << "\n";
			done = true;
		}
		if (nr == 0)
		{
			cout << "Wrong input" << "\n";
		}
	}
	
}

void UI::menu_user()
{
	cout << "Press 1 to see the tutorials with a given presenter" << "\n";
	cout << "Press 2 to delete a tutorial from watchlist" << "\n";
	cout << "Press 3 to see all the tutorial from watchlist" << "\n";
	cout << "Press 4 to exit the mode" << "\n";
}