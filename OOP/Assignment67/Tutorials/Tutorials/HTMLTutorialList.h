#pragma once
#include "RepoUser.h"
#include "AbsttractTutorialList.h"

class HTMLTutorialList : public RepoUser {
public:
	void write();
	void open();
};