#include "Service.h"

void Service::add(int id, string description, string area, vector<string> coord)
{
	Buildings b = Buildings( id, description, area, coord );
	this->r.add(b);
}

vector<Buildings> Service::getBuildingsSorted(string area)
{
	vector<Buildings> res;
	Buildings b;
	for (auto i : this->r.getBuildings())
		if (i.getArea() == area)
			res.push_back(i);
	for (auto i : this->getBuildings())
	{
		if (i.getArea() != area && i.getArea() != "office")
			res.push_back(i);
		if (i.getArea() == "office")
			b = i;
	}
	res.push_back(b);
	return res;
}
