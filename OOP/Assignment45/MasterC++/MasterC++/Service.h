#pragma once
#include <string>
#include "Repo.h"

class Service {
private:
	Repository* r;
public:
	/// <summary>
	/// The constructor
	/// </summary>
	Service();
	~Service() { delete this->r; };
	/// <summary>
	/// Adds an element.
	/// </summary>
	/// <param name="title"></param>
	/// <param name="presenter"></param>
	/// <param name="duration"></param>
	/// <param name="link"></param>
	/// <param name="likes"></param>
	/// <returns></returns>
	int add(const std::string& title, const std::string& presenter, const std::string& duration, const std::string& link, int likes);
	/// <summary>
	/// Removes an element.
	/// </summary>
	/// <param name="link"></param>
	/// <returns></returns>
	int remove_srv(std::string link);
	/// <summary>
	/// Updates an element
	/// </summary>
	/// <param name="_title"></param>
	/// <param name="_presenter"></param>
	/// <param name="_duration"></param>
	/// <param name="_link"></param>
	/// <param name="_likes"></param>
	/// <returns></returns>
	int update_srv(std::string _title, std::string _presenter, std::string _duration, std::string _link, int _likes);
	/// <summary>
	/// Get the repository.
	/// </summary>
	/// <returns></returns>
	/// 
	DynArray<MasterC>* presenters(std::string presenter) { return this->r->get_prestenters(presenter); };
	Repository* getRepo() { return this->r; };
	
};    