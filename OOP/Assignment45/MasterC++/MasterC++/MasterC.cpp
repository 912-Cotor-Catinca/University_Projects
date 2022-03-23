#include "MasterC.h"
#include <sstream>
#include <iostream>
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
	buffer <<"Tutorial \n\t title: "  << this->title << "\n" << "\t presenter: " << this->presenter << "\n" << "\t duration: " << this->duration << "\n" << "\t link: " << this->link << "\n" << "\t likes: " << this->likes << "\n";
	return buffer.str();
}
