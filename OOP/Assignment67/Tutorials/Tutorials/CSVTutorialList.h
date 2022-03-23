#pragma once
#include "AbsttractTutorialList.h"
#include "RepoUser.h"

class CSVTutorialList : public RepoUser {
public:
	void write();
	void open();
};