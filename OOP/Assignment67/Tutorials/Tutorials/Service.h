#pragma once
#include "Validator.h"
#include "Repo.h"

class Service {
private:
	Repo* r;
	Validator* val;
public:
	/// <summary>
	/// The constructor
	/// </summary>
	Service() { this->r = new Repo; this->val = new Validator; };
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

	int update_likes(string link) { return this->r->update_likes(link); };

	vector<MasterC> GetPresenters(string name) { return this->r->GetPresenters(name); };

	Repo* GetRepo() { return this->r; };
};