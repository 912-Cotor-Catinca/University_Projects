#pragma once
#include <stack>
#include "Repo.h"
class Command
{
public:
	Command() {};
	virtual ~Command() {};
	virtual void Execute() = 0;
	virtual void Undo() = 0;
	virtual void Redo() = 0;
};


class AddComman : public Command
{
private:
	MasterC elem;
	Repo* r;
public:
	AddComman(Repo* re, MasterC& t);
	void Execute();
	void Undo();
	void Redo();
};

class DeleteCommand : public Command
{
private:
	MasterC elem;
	Repo* r;
public:
	DeleteCommand(Repo* re, MasterC& t) : r{ re }, elem{ t } {}
	void Execute();
	void Undo();
	void Redo();
};

class UpdateCommand : public Command
{
private:
	MasterC elem;
	string title, presenter, duration, link;
	string prevtitle, prevpresenter, prevduration, prevlink;
	int likes, prevlikes;
	Repo* r;
public:
	UpdateCommand(Repo* re, MasterC& c,string _title, string _presenter, string _duration, string _link, int _like);
	void Execute();
	void Undo();
	void Redo();
};