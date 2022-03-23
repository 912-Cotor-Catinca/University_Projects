#pragma once
#include "RepoUser.h"
#include "AbsttractTutorialList.h"

class HTMLTutorialList : public RepoUser {
private:
	void load();
public:
	HTMLTutorialList();
	void write();
	void open();
};