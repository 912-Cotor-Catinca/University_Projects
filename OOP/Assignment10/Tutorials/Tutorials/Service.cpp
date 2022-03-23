#include "Service.h"

int Service::add(const std::string& title, const std::string& presenter, const std::string& duration, const std::string& link, int likes)
{
	MasterC c = MasterC(title, presenter, duration, link, likes);
	this->val->Validate_All(c);
	int a = this->r->add(c);
	return a;
	
}

int Service::remove_srv(std::string link)
{
	int a = this->r->remove(link);
	return a;
}

int Service::update_srv(std::string _title, std::string _presenter, std::string _duration, std::string _link, int _likes)
{
	MasterC c = MasterC(_title, _presenter, _duration, _link, _likes);
	this->val->Validate_All(c);
	int a = this->r->update(_title, _presenter, _link, _duration, _likes);
	return a;
}