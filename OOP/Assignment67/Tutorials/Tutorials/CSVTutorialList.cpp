#include "CSVTutorialList.h"
#include <fstream>

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
