#include "CSVTutorialList.h"
#include <fstream>

void CSVTutorialList::load()
{
	ifstream f("tutorials.csv");
	if (!f.is_open())
		return;
	MasterC c{};
	while (f >> c)
	{
		this->add(c);
	}
	f.close();
}

CSVTutorialList::CSVTutorialList()
{
	load();
}
void CSVTutorialList::write()
{
	std::ofstream file("tutorials.csv");
	for (auto i : this->GetArray())
		file << i;
	file.close();
}

void CSVTutorialList::open()
{
	system("notepad.exe tutorials.csv");
}
