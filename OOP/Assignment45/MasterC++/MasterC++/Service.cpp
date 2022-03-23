#include "Service.h"

Service::Service()
{
	this->r = new Repository;
}

int Service::add(const std::string& title, const std::string& presenter, const std::string& duration, const std::string& link, int likes)
{ 
	MasterC c = MasterC(title, presenter, duration, link, likes);
	int a = this->r->add(c);
	return a;
}

int Service::remove_srv(std::string link)
{
	int a = this->r->remove_tut(link);
	return a;
}

int Service::update_srv(std::string _title, std::string _presenter, std::string _duration, std::string _link, int _likes)
{
	int a = this->r->update_tut(_title, _presenter, _duration, _link, _likes);
	return a;
}