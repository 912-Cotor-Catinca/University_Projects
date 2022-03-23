#pragma once
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

class Ethonologist
{
private:
	string name;
	string area;
public:
	Ethonologist(string name, string area);
	Ethonologist();
	~Ethonologist();

	string getName() { return this->name; }
	string getArea() { return this->area; }

	friend ostream& operator<<(ostream& os, Ethonologist& i);
	friend istream& operator>>(istream& os, Ethonologist& i);
};

