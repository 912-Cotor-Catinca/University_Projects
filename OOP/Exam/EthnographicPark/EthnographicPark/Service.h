#pragma once
#include "Repository.h"
class Service
{
private:
	Repository& r;
public:
	Service(Repository& r) : r{ r }{}
	~Service(){}

	vector<Buildings> getBuildings() { return this->r.getBuildings(); }
	vector<Ethonologist> getEthnologists() { return this->r.getEthnologist(); }
	void add(int id, string description, string area, vector<string> coord);
	vector<Buildings> getBuildingsSorted(string area);
	void update(int id, string description, string area, vector<string> c) { this->r.update(id, description, area, c); }
};

