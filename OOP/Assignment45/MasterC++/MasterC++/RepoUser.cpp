#include "RepoUser.h"

RepoUser::RepoUser()
{
	this->watch_list = new DynArray<MasterC>(10);
}

RepoUser::~RepoUser()
{
	delete this->watch_list;
}

int RepoUser::add(MasterC c)
{
	int a = this->search_by_link(c.getLink());
	if (a != -1)
		return -1;
	this->watch_list->add(c);
	return 1;
}

bool RepoUser::remove(std::string link)
{
	int a = this->search_by_link(link);
	return this->watch_list->remove(this->watch_list->getElems()[a]);
}

int RepoUser::search_by_link(std::string link)
{
	for (int i = 0; i < this->watch_list->getLength(); ++i)
		if (this->watch_list->getElems()[i].getLink() == link)
			return i;
	return -1;
}

DynArray<MasterC>* RepoUser::getDynArray()
{
	return this->watch_list;
}