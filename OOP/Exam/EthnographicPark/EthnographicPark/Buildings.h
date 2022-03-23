#pragma once
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

class Buildings
{
private:
	int id;
	string description;
	string area;
	vector<string> coordinates;
public:
	Buildings(int id, string des, string area, vector<string> c);
	Buildings();
	~Buildings() {};

	int getId() { return this->id; }
	string getDescription() { return this->description; }
	string getArea() { return this->area; }
	vector<string> getCoordinates() { return this->coordinates; }
	string getCoordString() { 
		string res;
	for (auto i : this->coordinates)
		res += i;
	return res;
	}
	void setid(int new_val) { this->id = new_val; }
	void setDes(string newval) { this->description = newval; }
	void setArea(string newval) { this->area = newval; }
	void setCOOr(vector<string> newval) { this->coordinates = newval; }

	friend ostream& operator<<(ostream& os, Buildings& i);
	friend istream& operator>>(istream& os, Buildings& i);
};

