#pragma once
#include "AbsttractTutorialList.h"
#include "RepoUser.h"

class CSVTutorialList : public RepoUser {
private:
	void load();
public:
	CSVTutorialList();
	void write();
	void open();
};