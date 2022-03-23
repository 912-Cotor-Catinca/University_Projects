#pragma once
#include "MasterC.h"
#include "Exceptions.h"
#include <vector>
#include <iostream>
#include <fstream>
using namespace std;

class Repo
{
private:
	vector<MasterC> da;
public:
	Repo();
	~Repo();
	int add(MasterC elem);
	int update(string title, string name, string link, string duration, int likes);
	int update_likes(string link);
	bool remove(string link);
	int search_by_link(string link);
	int search_by_pres(string name);
	vector<MasterC> GetArray() { return this->da; };
	vector<MasterC> GetPresenters(string name);
	int GetSizeRepo() { return this->da.size(); };
	void ReadFile();
	void SaveFile();
};