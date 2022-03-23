#include "Repo.h"

Repository::Repository()
{
	this->da = new DynArray<MasterC>(100);
	init_list();
}

int Repository::add(MasterC c)
{
	if (search_by_link(c.getLink()) != -1)
		return -1;
	this->da->add(c);
	return 1;
}

int Repository::remove_tut(std::string link)
{
	if (search_by_link(link) == -1)
		return -1;
	else
		bool f = this->da->remove(this->da->getElems()[search_by_link(link)]);
	return 1;
}

int Repository::update_tut(std::string _title, std::string _presenter, std::string _duration, std::string _link, int _likes)
{
	if (search_by_link(_link) == -1)
		return -1;
	else
		this->da->update(this->da->getElems()[search_by_link(_link)], _title, _presenter, _duration, _likes);
	return 1;
}

int Repository::search_by_link(std::string link)
{
	bool found = false;
	for(int i = 0; i < this->da->getLength(); ++i)
		if (this->da->getElems()[i].getLink() == link)
		{
			found = true;
			return i;
		}
	return -1;
}

int Repository::search_by_pres(std::string pres)
{
	bool found = false;
	for (int i = 0; i < this->da->getLength(); ++i)
		if (this->da->getElems()[i].getPresenter() == pres)
		{
			found = true;
			return i;
		}
	return -1;
}

DynArray<MasterC>* Repository::getDynArray()
{
	return this->da;
}

DynArray<MasterC>* Repository::get_prestenters(std::string presenter)
{
	DynArray<MasterC> *pres = new DynArray<MasterC>(4);
	for (int i = 0; i < this->da->getLength(); ++i)
	{
		if (this->da->getElems()[i].getPresenter() == presenter)
			pres->add(this->da->getElems()[i]);
	}
	return pres;
}

void Repository::init_list()
{
	this->da->add(MasterC("C++ Programming Tutorial 1 - Intro to C++", "Caleb Curry", "12:12", "https://www.youtube.com/watch?v=OTroAxvRNbw", 6689));
	this->da->add(MasterC("C++ Programming Language Tutorial | List in C++ STL", "GeeksforGeeks", "2:28", "https://www.youtube.com/watch?v=VcCRXrLFxyc", 123));
	this->da->add(MasterC("POINTERS in C++", "The Cherno", "16:58", "https://www.youtube.com/watch?v=DTxHyVn0ODg", 17000));
	this->da->add(MasterC("C++ Tutorial for Beginners", "freeCodeCamp", "4:01:18", "https://www.youtube.com/watch?v=vLnPwxZdW4Y", 133000));
	this->da->add(MasterC("Arrays in C++", "The Cherno", "18:30", "https://www.youtube.com/watch?v=ENDaJi08jCU", 61000));
	this->da->add(MasterC("Limbajul C++ Pentru Incepatori", "SkyExpression.ro", "24:07", "https://www.youtube.com/watch?v=p5FXh2TsQLU", 337));
	this->da->add(MasterC("C++ Tutorial 10 : Object Oriented Programming", "Derek banas", "40:07", "https://www.youtube.com/watch?v=ZOKLjJF54Xc", 32000));
	this->da->add(MasterC("Object-oriented Programming in 7 minutes", "Mosh", "7:13", "https://www.youtube.com/watch?v=pTB0EiLXUC8" ,49000));
	this->da->add(MasterC("C++ Tutorial 2020", "Derek banas", "22:45", "https://www.youtube.com/watch?v=6y0bp-mnYU0", 45000));
	this->da->add(MasterC("Bjarne Stroustrup - The Essence of C++", "The University of Edinburgh", "29:10", "https://www.youtube.com/watch?v=86xWVb4XIyE", 96000));
	this->da->add(MasterC("C++ Tutorial 9:Lambda Expressions", "Derek banas","25:20", "https://www.youtube.com/watch?v=482weZjwVHY", 767));
	this->da->add(MasterC("C++ Tutorial 2 : Conditionals, Arrays, Vectors, Strings, Loops", "Derek banas","32:13", "https://www.youtube.com/watch?v=tT8ICXAO_-4", 1200));

}