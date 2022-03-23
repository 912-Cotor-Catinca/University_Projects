#include "ServiceUser.h"
#include <iostream>

ServiceUser::ServiceUser()
{
}

ServiceUser::~ServiceUser()
{
}

int ServiceUser::add(MasterC c)
{
	return this->r->add(c);
}

bool ServiceUser::remove(std::string link)
{
	return this->r->remove(link);
}


int ServiceUser::update(string link)
{
	return this->r->update(link);
}

void ServiceUser::updateTutorial(int index, const MasterC& c)
{
	this->r->updateTutorial(index, c);

}

void ServiceUser::setRepo(std::string type)
{
	if (type == "html")
		this->r = new HTMLTutorialList;
	else
		this->r = new CSVTutorialList;
}