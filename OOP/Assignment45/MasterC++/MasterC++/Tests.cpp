#include "Tests.h"
#include "MasterC.h"
#include "DynArray.h"
#include "Repo.h"
#include "RepoUser.h"
#include "Service.h"
#include "ServiceUser.h"
#include <cassert>
#include <iostream>

Test::Test()
{}

void Test::Test_Array()
{
	DynArray<MasterC> da = DynArray<MasterC>(2);
	MasterC m = MasterC("tutorial", "alex", "12:12", "www.asdf.ro", 80);
	da.add(m);
	assert(da.getLength() == 1);
	MasterC m2 = MasterC("tutorial1", "alexandru", "15:12", "www.asdsdf.ro", 10);
	da.add(m2);
	assert(da.getLength() == 2);
	MasterC m3 = MasterC("tutorial C", "dan asde", "10:45", "www.tutoriale.ro", 140);
	da.add(m3);
	assert(da.getCapacity() == 4);
	assert(da.getLength() == 3);
	da.remove(m3);
	assert(da.getLength() == 2);
	//assert(da.getLength() == 2);
	da.update(m2, "new", "idk", "9:08", 4);
	
	//m3.setTitle("new");
	assert(da.getElems()[1].getTitle()=="new");
	MasterC m4 = MasterC("newnew", "idkidk", "19:08", "www.oop.ro", 4);
	bool f = da.remove(m4);
	assert(f == false);
	da.update(m4, "new", "idk", "9:08", 4);
	//DynArray da1 = DynArray(da);
	//da1 = da;

}

void Test::Test_Domain()
{
	//MasterC m0 = MasterC();
	MasterC m1 = MasterC("tutorial", "alex", "12:12", "www.asdf.ro", 80);
	assert(m1.getLikes() == 80);
	assert(m1.getDuration() == "12:12");
	assert(m1.getLink() == "www.asdf.ro");
	assert(m1.getPresenter() == "alex");
	assert(m1.getTitle() == "tutorial");

	m1.setLikes(71);
	assert(m1.getLikes() == 71);
	m1.setPres("new");
	assert(m1.getPresenter() == "new");
	m1.setTitle("new title");
	assert(m1.getTitle() == "new title");
	string str = m1.toString();
}

void Test::Test_Repo()
{
	Repository r;
	MasterC m1 = MasterC("tutorial", "alex", "12:12", "www.asdf.ro", 80);
	r.add(m1);
	int c = r.search_by_link("link");
	r.remove_tut("link");
	assert(r.getDynArray()->getLength() == 13);
	MasterC m2 = MasterC("tutorial1", "alexandru", "15:12", "www.asdsdf.ro", 10);
	r.add(m2);
	int f = r.search_by_pres("alex");
	int f1 = r.search_by_pres("alex1");
	assert(r.getDynArray()->getLength() == 14);
	int n = r.remove_tut("www.asdsdf.ro");
	assert(n == 1);
	assert(r.getDynArray()->getLength() == 13);
	int m = r.update_tut("new", "idk", "10:10", "www.asdf.ro", 457);
	assert(m == 1);
	int a = r.update_tut("new", "idk", "10:10", "www.link.ro", 45);
	assert(a == -1);
	int b = r.remove_tut("www.link.ro");
	assert(b == -1);
	int s = r.search_by_link("www.asdf.ro");
	assert(s == 12);
	int s1 = r.search_by_link("sayalink");
	assert(s1 == -1);
	
}

void Test::Test_Service()
{
	Service s;
	s.add("tutorial", "alex", "12:12", "www.asdf.ro", 80);
	s.add("new", "idk", "10:10", "www.asdf.ro", 457);
	assert(s.getRepo()->getDynArray()->getLength() == 13);
	s.remove_srv("www.asdf.ro");
	assert(s.getRepo()->getDynArray()->getLength() == 12);
	s.add("new", "idk", "10:10", "www.asdf.ro", 457);
	s.update_srv("newnew", "idkidk", "10:11", "www.asdf.ro", 45);
	DynArray<MasterC>* arr = s.presenters("alex");
}

void Test::Test_RepoUser()
{
	RepoUser r_user;
	MasterC m1 = MasterC("tutorial", "alex", "12:12", "www.asdf.ro", 80);
	MasterC m2 = MasterC("tutorial1", "alexandru", "15:12", "www.asdsdf.ro", 10);
	int a = r_user.add(m1);
	assert(a == 1);
	int b = r_user.add(m1);
	assert(b == -1);
	b = r_user.add(m2);
	bool f = r_user.remove("www.asdf.ro");
	assert(r_user.getDynArray()->getLength() == 1);
}

void Test::Test_ServUser()
{
	ServiceUser s;
	MasterC m1 = MasterC("tutorial", "alex", "12:12", "www.asdf.ro", 80);
	MasterC m2 = MasterC("tutorial1", "alexandru", "15:12", "www.asdsdf.ro", 10);
	s.add(m1);
	s.add(m2);
	assert(s.getRepoUser()->getDynArray()->getLength() == 2);
	s.remove("www.asdf.ro");
	assert(s.getRepoUser()->getDynArray()->getLength() == 1);
}