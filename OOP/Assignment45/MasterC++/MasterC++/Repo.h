#pragma once
#include "DynArray.h"

class Repository {
private:
	DynArray<MasterC> *da;

public:
	/// <summary>
	/// The constructor
	/// </summary>
	Repository();
	~Repository() { delete this->da; };
	/// <summary>
	/// Adds an element in the repository
	/// </summary>
	/// <param name="c"></param>
	/// <returns></returns>
	int add(MasterC c);
	/// <summary>
	/// Search an element by the link which is uniq
	/// </summary>
	/// <param name="link"></param>
	/// <returns></returns>
	int search_by_link(std::string link);
	/// <summary>
	/// Remove an element by link
	/// </summary>
	/// <param name="link"></param>
	/// <returns></returns>
	int remove_tut(std::string link);
	/// <summary>
	/// Updates an element by link
	/// </summary>
	/// <param name="_title">The new title</param>
	/// <param name="_presenter">The new presenter</param>
	/// <param name="_duration">The new duration</param>
	/// <param name="_link"></param>
	/// <param name="_likes"The new number of likes></param>
	/// <returns></returns>
	int update_tut(std::string _title, std::string _presenter, std::string _duration, std::string _link, int _likes);
	/// <summary>
	/// Gets the dynamic array
	/// </summary>
	/// <returns></returns>
	DynArray<MasterC>* getDynArray();
	/// <summary>
	/// Initialize the repository
	/// </summary>
	void init_list();
	DynArray<MasterC>* get_prestenters(std::string presenter);
	int search_by_pres(std::string pres);
};