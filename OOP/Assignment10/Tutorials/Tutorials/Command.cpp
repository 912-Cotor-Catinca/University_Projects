#include "Command.h"

AddComman::AddComman(Repo* re, MasterC& t) : r{re}, elem{t}
{
}

void AddComman::Execute()
{
	this->r->add(this->elem);
}

void AddComman::Undo()
{
	this->r->remove(this->elem.getLink());
}

void AddComman::Redo()
{
	this->r->add(this->elem);
}

void DeleteCommand::Execute()
{
	this->r->remove(this->elem.getLink());
}

void DeleteCommand::Undo()
{
	this->r->add(this->elem);
}

void DeleteCommand::Redo()
{
	this->r->remove(this->elem.getLink());
}

UpdateCommand::UpdateCommand(Repo* re, MasterC&c, string _title, string _presenter, string _duration, string _link, int _like) : r{re}, elem{c}, 
							title{_title}, presenter{_presenter}, duration{_duration}, link{_link}, likes{_like}
{
	MasterC t = this->r->GetArray()[this->r->search_by_link(this->elem.getLink())];
	this->prevtitle = t.getTitle();
	this->prevpresenter = t.getPresenter();
	this->prevduration = t.getDuration();
	this->prevlikes = t.getLikes();
	this->prevlink = t.getLink();
}

void UpdateCommand::Execute()
{	
	this->r->update(this->title, this->presenter, this->link, this->duration, this->likes);
}

void UpdateCommand::Undo()
{
	this->r->update(this->prevtitle, this->prevpresenter, this->prevlink, this->prevduration, this->prevlikes);
}

void UpdateCommand::Redo()
{
	this->r->update(this->title, this->presenter, this->link, this->duration, this->likes);
}
