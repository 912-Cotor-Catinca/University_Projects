#include "Repo.h"
#include <algorithm>
#include <iostream>

Repo::Repo()
{
	ReadFile();
}

Repo::~Repo()
{
	//SaveFile();
	this->da.clear();
}

int Repo::add(MasterC elem)
{
	if (search_by_link(elem.getLink()) != -1)
	{
		throw (Exception("The tutorial is invalid"));
		return -1;
	}
		
	else
	{
		this->da.push_back(elem);
		SaveFile();
		return 1;
	}
}

int Repo::search_by_link(string link)
{
	auto i = [&](MasterC c) {return c.getLink() == link; };
	auto it = find_if(this->da.begin(), this->da.end(), i);
	if (it != this->da.end())
		return it - this->da.begin();
	else
		return -1;
}

int Repo::search_by_pres(string name)
{
	auto i = [&](MasterC c) {return c.getPresenter() == name; };
	auto it = find_if(this->da.begin(), this->da.end(), i);
	if (it != this->da.end())
		return it - this->da.begin();
	else
		return -1;
}

vector<MasterC> Repo::GetPresenters(string name)
{
	vector<MasterC> presenters;
	for (auto it = this->da.begin(); it != this->da.end(); ++it)
		if (this->da[it - this->da.begin()].getPresenter() == name)
			presenters.push_back(*it);

	return presenters;
}

bool Repo::remove(string link)
{
	if (search_by_link(link) == -1)
	{
		throw(Exception("The tutorial does not exist!"));
		return false;
	}
		
	int i = search_by_link(link);
	this->da.erase(this->da.begin() + i);
	SaveFile();
	return true;
}

int Repo::update(string title, string name, string link, string duration, int likes)
{
	if (search_by_link(link) == -1)
	{
		throw (Exception("The tutorial already exists"));
		return -1;
	}
		
	int it = search_by_link(link);
	this->da[it].setLikes(likes);
	this->da[it].setTitle(title);
	this->da[it].setPres(name);
	this->da[it].setDuration(duration);
	SaveFile();
	return 1;
}

int Repo::update_likes(string link)
{
	if (search_by_link(link) == -1)
		return -1;
	int it = search_by_link(link);
	int likes = this->da[it].getLikes();
	likes++;
	this->da[it].setLikes(likes);
	SaveFile();
	return 1;
}

void Repo::ReadFile()
{
	ifstream f("Tutorials.txt");
	if (!f.is_open())
		return;
	MasterC c{};
	while (f >> c)
	{
		this->da.push_back(c);
	}
	f.close();
}

void Repo::SaveFile()
{
	ofstream f("Tutorials.txt");
	if (!f.is_open())
		return;

	for (auto c : this->da)
		f << c;

	f.close();
}
