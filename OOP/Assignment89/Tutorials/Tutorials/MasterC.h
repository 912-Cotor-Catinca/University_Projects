#pragma once
#pragma once
#include <string>
#include <iostream>
using namespace std;

class MasterC {
private:
	std::string title;
	std::string presenter;
	std::string duration;
	std::string link;
	int likes;

public:
	MasterC(); //default constructor
	/// <summary>
	/// The Constructor
	/// </summary>
	/// <param name="_title"></param>
	/// <param name="_presenter"></param>
	/// <param name="_duration"></param>
	/// <param name="_link"></param>
	/// <param name="_likes"></param>
	MasterC(std::string _title, std::string _presenter, std::string _duration, std::string _link, int _likes);
	/// <summary>
	/// Copy constructor
	/// </summary>
	/// <param name="c"></param>
	MasterC(const MasterC& c);
	///The destructor
	~MasterC();
	/// <summary>
	/// Get the title of a tutorial
	/// </summary>
	/// <returns></returns>
	std::string getTitle() const;
	/// <summary>
	/// Get the presenter of a tutorial
	/// </summary>
	/// <returns></returns>
	std::string getPresenter() const;
	/// <summary>
	/// Get the duration of a tutorial
	/// </summary>
	/// <returns></returns>
	std::string getDuration()const;
	/// <summary>
	/// Get the link of a tutorial
	/// </summary>
	/// <returns></returns>
	std::string getLink() const;
	/// <summary>
	/// Set the title of a tutorial
	/// </summary>
	/// <param name="new_val"></param>
	void setTitle(std::string new_val) { this->title = new_val; };
	/// <summary>
	///  Set the presenter of a tutorial
	/// </summary>
	/// <param name="new_val"></param>
	void setPres(std::string new_val) { this->presenter = new_val; };
	/// <summary>
	///  Set the duration of a tutorial
	/// </summary>
	/// <param name="new_val"></param>
	void setDuration(std::string new_val) { this->duration = new_val; };
	/// <summary>
	///  Set the number of likes of a tutorial
	/// </summary>
	/// <param name="new_val"></param>
	void setLikes(int new_val) { this->likes = new_val; };
	/// <summary>
	///  Get the number of likes of a tutorial
	/// </summary>
	/// <returns></returns>
	int getLikes()const;
	/// <summary>
	/// The string function
	/// </summary>
	/// <returns></returns>
	std::string toString() const;
	friend istream& operator>>(istream& is, MasterC& c);
	friend ostream& operator<<(ostream& os, MasterC& c);
};