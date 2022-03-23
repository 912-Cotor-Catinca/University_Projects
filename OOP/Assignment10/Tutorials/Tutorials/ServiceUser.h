#pragma once
#include "RepoUser.h"
#include "HTMLTutorialList.h"
#include "CSVTutorialList.h"

class ServiceUser {
private:
	RepoUser* r;
public:
	/// <summary>
	/// The constructor
	/// </summary>
	ServiceUser();
	///The destructor
	~ServiceUser();
	/// <summary>
	/// Adds an element in service
	/// </summary>
	/// <param name="c"></param>
	/// <returns>1 if the element can be added(it does not exist), -1 otherwise</returns>
	int add(MasterC c);
	/// <summary>
	/// Removes an element from service
	/// </summary>
	/// <param name="link"></param>
	/// <returns>true if the element exists, false otherwise</returns>
	bool remove(std::string link);
	/// <summary>
	/// Get the user repository
	/// </summary>
	/// <returns></returns>
	RepoUser* getRepoUser() { return this->r; };
	vector<MasterC> GetWatchList() { return this->r->GetArray(); }
	int update(string link);
	void updateTutorial(int index, const MasterC& c);
	void setRepo(std::string type);
};