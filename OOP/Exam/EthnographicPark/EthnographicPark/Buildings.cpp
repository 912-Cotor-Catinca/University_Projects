#include "Buildings.h"

Buildings::Buildings(int id, string des, string area, vector<string> c) : id{id}, description{des}, area{area}, coordinates{c}
{
}

Buildings::Buildings()
{
	id = 0;
	description = "";
	area = "";
	coordinates = vector<string>();
}

vector<string> tokenize1(string str, char delimiter)
{
	stringstream buff(str);
	string token;
	vector<string> res;
	while (getline(buff, token, delimiter))
		res.push_back(token);
	return res;
}

ostream& operator<<(ostream& os, Buildings& i)
{
	os << to_string(i.id) << ";" << i.description << ";" << i.area << ";";
	int n = i.coordinates.size();
	int p = 0;
	while (n > 1)
	{
		os << i.coordinates[p] << ";";
		p++;
		n--;
	}
	os << i.coordinates[p] << "\n";
	return os;
}

istream& operator>>(istream& os, Buildings& i)
{
	string line;
	getline(os, line);
	vector<string> tokens = tokenize1(line, ';');
	if (tokens.size() <= 3)
		return os;
	i.id = stoi(tokens[0]);
	i.description = tokens[1];
	i.area = tokens[2];
	vector<string> c;
	for (int j = 3; j < tokens.size(); ++j)
		c.push_back(tokens[j]);
	i.coordinates = c;
	return os;
}
