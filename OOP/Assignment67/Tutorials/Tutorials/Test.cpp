#include "Test.h"
#include "Repo.h"
#include <assert.h>
#include <iostream>

void Test_Repo()
{
	Repo r;
	MasterC m1 = MasterC("sdfg", "dsfgh", "15:45", "www.anjd.as", 45);
	MasterC m2 = MasterC("sdasdfg", "asdexdsfgh", "15:45", "www.aanjd.as", 45);
	r.add(m1);
	assert(r.GetArray().size() == 3);
	r.add(m2);
	assert(r.GetArray().size() == 4);
	int a = r.search_by_link("www.aanjd.as");
	assert(a == 3);
	bool f = r.remove("www.aanjd.as");
	assert(r.GetArray().size() == 3);
	int b = r.update("de ce", "idc", "www.anjd.as", "13:22", 21);
	assert(b == 1);
	assert(r.GetArray()[2].getLikes() == 21);
	MasterC m3 = MasterC("edcfr", "idc", "14:44", "www.link.ro", 47);
	r.add(m3);
	vector<MasterC> pres = r.GetPresenters("Caleb Curry");
	for (auto it = pres.begin(); it != pres.end(); ++it)
		std::cout << pres[it - pres.begin()].toString() << ' ' << pres[it - pres.begin()].getLink() << ' ';
 
}