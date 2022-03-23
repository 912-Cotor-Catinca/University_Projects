#include "Repository.h"

Repository::Repository()
{
	loadE();
	loadB();
}

Repository::~Repository()
{
}

void Repository::loadE()
{
	ifstream fin("ethnologists.txt");
	if (!fin.is_open())
		return;
	Ethonologist e{};
	while (fin >> e)
		this->eths.push_back(e);
	fin.close();
}

void Repository::loadB()
{
	ifstream f("buildings.txt");
	if (!f.is_open())
		return;
	Buildings e{};
	while (f >> e)
		this->builds.push_back(e);
	f.close();
}


void Repository::SaveE()
{
	ofstream fout("ethnologists.txt");
	if (!fout.is_open())
		return;
	for (auto i : this->eths)
		fout << i;
	fout.close();
}

void Repository::SaveB()
{
	ofstream f("buildings.txt");
	if (!f.is_open())
		return;
	for (auto i : this->builds)
		f << i;
	f.close();
}

void Repository::add(Buildings b)
{
	if (b.getDescription().empty())
		throw exception("the descrinption is empty!");
	if (search_by_id(b.getId()) != -1)
		throw exception("The building already exists!");
	if (search_by_coordinates(b.getCoordinates()) != -1)
		throw exception("The building overlap!");
	/*string c = b.getCoordString();
	string dig;
	for (int i = 0; i < c.length(); ++i)
		if (isdigit(c[i]))
			dig += c[i];
	for(int i = 0; i < dig.length(); ++i)
		for(int j = 1; j < dig.length(); ++i)
			if(dig[i] > dig[j])
				throw exception("The buildings are not located over a set of continuous squares!");*/

	this->builds.push_back(b);
	SaveB();
}

int Repository::search_by_id(int id)
{
	auto i = [&](Buildings b) {return b.getId() == id; };
	auto it = find_if(this->builds.begin(), this->builds.end(), i);
	if (it != this->builds.end())
		return it - this->builds.begin();
	else
		return -1;
}

int Repository::search_by_coordinates(vector<string> coor)
{
	auto i = [&](Buildings b) {return b.getCoordinates() == coor; };
	auto it = find_if(this->builds.begin(), this->builds.end(), i);
	if (it != this->builds.end())
		return it - this->builds.begin();
	else
		return -1;
}

void Repository::update(int id, string description, string area, vector<string> c)
{
	if (description.empty())
		throw exception("the descrinption is empty!");
	if (search_by_id(id) == -1)
		throw exception("The building already exists!");
	if (search_by_coordinates(c) != -1)
		throw exception("The building overlap!");
	int i = search_by_id(id);

	this->builds[i].setid(id);
	this->builds[i].setDes(description);
	this->builds[i].setArea(area);
	this->builds[i].setCOOr(c);
	SaveB();
}
