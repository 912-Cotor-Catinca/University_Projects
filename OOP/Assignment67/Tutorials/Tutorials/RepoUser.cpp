#include "RepoUser.h"

int RepoUser::add(MasterC c)
{
	if (search_by_link(c.getLink()) != -1)
		return -1;
	else
	{
		this->watch_list.push_back(c);
		write();
		return 1;
	}
}

int RepoUser::search_by_link(string link)
{
	auto i = [&](MasterC c) {return c.getLink() == link; };
	auto it = find_if(this->watch_list.begin(), this->watch_list.end(), i);
	if (it != this->watch_list.end())
		return it - this->watch_list.begin();
	else
		return -1;
}

bool RepoUser::remove(std::string link)
{
	if (search_by_link(link) == -1)
		return false;
	int i = search_by_link(link);
	this->watch_list.erase(this->watch_list.begin() + i);
	write();
	return true;
}

int RepoUser::update(string link)
{
	if (search_by_link(link) == -1)
		return -1;
	int it = search_by_link(link);
	int likes = this->watch_list[it].getLikes();
	likes++;
	this->watch_list[it].setLikes(likes);
	write();
}