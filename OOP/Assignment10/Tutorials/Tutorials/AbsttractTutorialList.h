#pragma once
#include "RepoUser.h"
#include "ServiceUser.h"

class AbstractTutorialList {
protected:
	vector<MasterC> data;
public:
	AbstractTutorialList() {};
	virtual void write() = 0;
	virtual void open() = 0;
	vector<MasterC> getData() { return this->data; };
	void setData(vector<MasterC> newdata) { for (auto i : newdata) data.push_back(i); };
};