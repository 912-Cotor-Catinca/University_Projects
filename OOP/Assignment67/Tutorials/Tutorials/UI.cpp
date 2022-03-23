#include "UI.h"
#include <iostream>
#include <stdio.h>
#include "Validator.h"
#include "AbsttractTutorialList.h"
#include "CSVTutorialList.h"
#include "HTMLTutorialList.h"
#include <shlobj.h>
#include <shlwapi.h>
#include <objbase.h>
using namespace std;

UI::UI()
{
	this->s = new Service;
	this->s_user = new ServiceUser;
	this->val = new Validator;
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
	
	try
	{
		this->val->Validate_All(MasterC(title, presenter, duration, link, likes));
		int a = this->s->add(title, presenter, duration, link, likes);
	}
	catch (ValidationException& ex)
	{
		cout << ex.getMessage();
		//return;
	}
}

void UI::remove()
{
	std::string link;
	cout << "The link: ";
	getline(cin, link);
	getline(cin, link);
	try {
		int a = this->s->remove_srv(link);
	}
	catch (ValidationException& ex)
	{
		cout << ex.getMessage();
	}
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

	try
	{
		this->val->Validate_All(MasterC(title, presenter, duration, link, likes));
		int a = this->s->update_srv(title, presenter, duration, link, likes);
		if (a == -1)
			cout << "The tutorial with given link does not exist" << "\n";
	}
	catch (ValidationException& ex)
	{
		cout << ex.getMessage();
	}
}

void UI::display()
{
	vector<MasterC> da = this->getService()->GetRepo()->GetArray();
	for (auto i = da.begin(); i != da.end(); ++i)
		cout << da[i - da.begin()].toString();
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
	if (this->s_user->getRepoUser()->GetArray().size() == 0)
		cout << "The watch list is empty" << "\n";
	vector<MasterC> da = this->s_user->getRepoUser()->GetArray();
	for (auto i : da)
		cout << i.toString();
}

void UI::delete_watchlist()
{
	std::string link;
	cout << "The link: ";
	getline(cin, link);
	getline(cin, link);
	bool a = this->s_user->remove(link);
	if (a == false)
		cout << "The tutorial is not in the watch list" << "\n";
	cout << "If you liked the tutorial, give it a like! Like it? yes or no >>";
	string answ;
	cin >> answ;
	if (answ == "yes")
	{
		this->s->update_likes(link);
	}
}

void UI::add_watchlist()
{
	string presenter;
	vector<MasterC> pres;
	bool f = false;
	while (!f)
	{
		cout << "The name of the presenter: ";
		getline(cin, presenter);
		getline(cin, presenter);
		if (this->getService()->GetRepo()->search_by_pres(presenter) != -1 || presenter.length() == 0)
			f = true;
		else
			cout << "The given presenter does not exists" << "\n";
	}

	if (presenter.length() == 0)
		pres = this->getService()->GetRepo()->GetArray();
	else
		pres = this->s->GetPresenters(presenter);
	bool done = false;
	while (!done)
	{
		for (auto i = pres.begin(); i != pres.end() && !done; ++i)
		{
			cout << pres[i - pres.begin()].toString();
			string link = pres[i - pres.begin()].getLink();
			wstring s_t = wstring(link.begin(), link.end());
			LPCWSTR s1 = TEXT("open");
			LPCWSTR s2 = s_t.c_str();
			ShellExecute(NULL, s1, s2, NULL, NULL, SW_SHOWNORMAL);
			string answ;
			cout << "Do you like the tutorial? If yes add in the watchlist! yes or no >> ";
			cin >> answ;
			if (answ == "yes")
			{
				int a = this->s_user->add(pres[i - pres.begin()]);
				if (a != 1)
					cout << "The tutorial has been already added! " << "\n";
			}
			string answ2;
			cout << "Continue with tutorials? yes or no >> ";
			cin >> answ2;
			if (answ2 == "yes")
			{
				if (i == pres.end())
					i = pres.begin();
				else continue;
			}
			if (answ2 == "no")
			{
				done = true;
			}
		}
	}
	
}

void UI::start_user()
{
	bool done = false;
	string option;
	cout << "In which format do you want to save the file?" << "\n";
	cout << "1. CSV file format or 2. HTML format --->";
	cin >> option;
	int nr = stoi(option);
	if (nr == 1)
		this->s_user->setRepo("csv");
	else
		this->s_user->setRepo("html");
	
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
		if (nr == 2)
		{
			this->delete_watchlist();
		}
		if (nr == 3)
		{
			this->display_watchlist();
		}
		if (nr == 4)
		{
			this->s_user->getRepoUser()->open();
		}
		if (nr == 5)
		{
			cout << "Goodbye dear user!" << "\n";
			done = true;
		}

	}
}

void UI::start_admin()
{
	bool done = false;
	while (!done)
	{
		try {
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
		}
		catch (Exception& ex)
		{
			std::cout << ex.what();
		}
	}

}

void UI::menu_user()
{
	cout << "Press 1 to see the tutorials with a given presenter" << "\n";
	cout << "Press 2 to delete a tutorial from watchlist" << "\n";
	cout << "Press 3 to see all the tutorial from watchlist" << "\n";
	cout << "Press 4 to open and see the watchlist" << "\n";
	cout << "Press 5 to exit the mode" << "\n";
}