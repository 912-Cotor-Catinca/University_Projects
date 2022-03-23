#pragma once
#include "Ethonologist.h"
#include "Buildings.h"
#include <fstream>

class Repository
{
private:
	vector<Ethonologist> eths;
	vector<Buildings> builds;
	void loadE();
	void loadB();
public:
	Repository();
	~Repository();

	void SaveE();
	void SaveB();

	vector<Ethonologist> getEthnologist() { return this->eths; }
	vector<Buildings> getBuildings() { return this->builds; }
	void add(Buildings b);
	int search_by_id(int id);
	int search_by_coordinates(vector<string> coor);
	void update(int id, string description, string area, vector<string> c);
};

