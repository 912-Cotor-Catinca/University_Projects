#include "HTMLTutorialList.h"
#include <fstream>
#include <shlobj.h>
#include <shlwapi.h>
#include <objbase.h>

void HTMLTutorialList::write()
{
	std::ofstream file("tutorials.html");
	file << "<!DOCTYPE html>" << "\n";
	file << "<html>" << "\n";
	file << "<head>" << "\n";
	file << "<title>Tutorials List</title>" << "\n";
	file << "</head>" << "\n";

	file << "<body>" << "\n";
	file << "<table border=\"1\">" << "\n";

	file << "<tr>" << "\n";
	file << "<td>Title</td>" << "\n";
	file << "<td>Presenter</td>" << "\n";
	file << "<td>Duration</td>" << "\n";
	file << "<td>Likes</td>" << "\n";
	file << "<td>Link</td>" << "\n";
	file << "</tr>" << "\n";

	vector<MasterC> da = this->GetArray();

	for (auto i = da.begin(); i != da.end(); ++i)
	{
		file << "<tr>" << "\n";
		file << "<td>" << da[i - da.begin()].getTitle() << "</td>" << "\n";
		file << "<td>" << da[i - da.begin()].getPresenter() << "</td>" << "\n";
		file << "<td>" << da[i - da.begin()].getDuration() << "</td>" << "\n";
		file << "<td>" << to_string(da[i - da.begin()].getLikes()) << "</td>" << "\n";
		file << "<td><a href=" << da[i - da.begin()].getLink() << ">Link</a></td>" << "\n";
		file << "</tr>" << "\n";
	}

	file << "</table>" << "\n";

	file << "</body>" << "\n";

	file << "</html>" << "\n";
	file.close();
}

void HTMLTutorialList::open()
{
	ShellExecuteA(NULL, NULL, "chrome.exe", "file:///C:/Users/Catinca/source/repos/a67-912-Cotor-Catinca/Tutorials/Tutorials/tutorials.html", NULL, SW_SHOWMAXIMIZED);
	system("PAUSE");
}
