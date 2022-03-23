#pragma once
#include "Repo.h"

class RepoUser {
private:
	vector<MasterC> watch_list;
public:
	/// <summary>
	/// The constructor
	/// </summary>
	RepoUser() {};
	///The destructor
	~RepoUser() {};
	/// <summary>
	/// Adds an element in the user repository
	/// </summary>
	/// <param name="c">The MasterC element</param>
	/// <returns>1 if the element can be added(it does not exist), -1 otherwise</returns>
	int add(MasterC c);
	/// <summary>
	/// Deletes an element from the user repository
	/// </summary>
	/// <param name="link">The link of the element which is deleted</param>
	/// <returns>true if the element exists, false otherwise</returns>
	bool remove(std::string link);
	/// <summary>
	/// Search an element by link
	/// </summary>
	/// <param name="link"></param>
	/// <returns>the position if the element exists, -1 otherwise</returns>
	int search_by_link(std::string link);
	/// <summary>
	/// Get the dynamic array.
	/// </summary>
	/// <returns></returns>
	
	int update(string link);

	vector<MasterC>  GetArray() { return this->watch_list; };

	virtual void SaveFile();
	virtual void write() {};
	virtual void open() = 0;
};