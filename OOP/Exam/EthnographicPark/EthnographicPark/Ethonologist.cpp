#include "Ethonologist.h"

Ethonologist::Ethonologist(string name, string area) : name{name}, area{area}
{
}

Ethonologist::Ethonologist()
{
	name = "";
	area = "";
}

Ethonologist::~Ethonologist()
{
}

vector<string> tokenize(string str, char delimiter)
{
	stringstream buff(str);
	string token;
	vector<string> res;
	while (getline(buff, token, delimiter))
		res.push_back(token);
	return res;
}

ostream& operator<<(ostream& os, Ethonologist& i)
{
	os << i.name << ";" << i.area << "\n";
	return os;
}

istream& operator>>(istream& os, Ethonologist& i)
{
	string line;
	getline(os, line);
	vector<string> tokens = tokenize(line, ';');
	if (tokens.size() != 2)
		return os;
	i.name = tokens[0];
	i.area = tokens[1];

	return os;
}
