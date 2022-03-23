#include "MasterC.h"
#include <sstream>
#include <vector>
using namespace std;

MasterC::MasterC(string _title, string _presenter, string _duration, string _link, int _likes)
{
	this->title = _title;
	this->presenter = _presenter;
	this->duration = _duration;
	this->link = _link;
	this->likes = _likes;
}

MasterC::MasterC()
{
	this->title = "";
	this->presenter = "";
	this->duration = "";
	this->link = "";
	this->likes = 0;
}

MasterC::MasterC(const MasterC& c)
{
	this->title = c.title;
	this->duration = c.duration;
	this->presenter = c.presenter;
	this->link = c.link;
	this->likes = c.likes;
}

string MasterC::getTitle() const
{
	return title;
}
std::string MasterC::getPresenter() const
{
	return presenter;
}
std::string MasterC::getDuration() const
{
	return duration;
}

std::string MasterC::getLink() const
{
	return link;
}

int MasterC::getLikes() const
{
	return likes;
}

MasterC::~MasterC()
{}

std::string MasterC::toString() const
{
	stringstream buffer;
	buffer << "Tutorial \n\t title: " << this->title << "\n" << "\t presenter: " << this->presenter << "\n" << "\t duration: " << this->duration << "\n" << "\t link: " << this->link << "\n" << "\t likes: " << this->likes << "\n";
	return buffer.str();
}

vector<string> token(string str, char delimiter)
{
	vector<string> result;
	stringstream buffer(str);
	string token;
	while (getline(buffer, token, delimiter))
		result.push_back(token);
	return result;
}

istream& operator>>(istream& is, MasterC& c)
{
	string line;
	getline(is, line);
	vector<string> tokens = token(line, ',');

	if (tokens.size() != 5)
		return is;
	c.title = tokens[0];
	c.presenter = tokens[1];
	c.duration = tokens[2];
	c.link = tokens[3];
	c.likes = stoi(tokens[4]);
	return is;
}

ostream& operator<<(ostream& os, MasterC& c)
{
	os << c.title << "," << c.presenter << "," << c.duration << "," << c.link << "," << to_string(c.likes) << "\n";
	return os;
}