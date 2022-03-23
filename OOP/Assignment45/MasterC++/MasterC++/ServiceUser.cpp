#include "ServiceUser.h"

ServiceUser::ServiceUser()
{
	this->r = new RepoUser();
}

ServiceUser::~ServiceUser()
{
	delete this->r;
}

int ServiceUser::add(MasterC c)
{
	return this->r->add(c);
}

bool ServiceUser::remove(std::string link)
{
	return this->r->remove(link);
}
