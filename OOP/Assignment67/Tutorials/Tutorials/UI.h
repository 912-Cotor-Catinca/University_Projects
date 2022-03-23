#pragma once
#include "Service.h"
#include "ServiceUser.h"
#include "Validator.h"

class UI {
private:
	Service* s;
	ServiceUser* s_user;
	Validator* val;

public:
	UI();
	~UI() { delete this->s; delete this->s_user; };
	void add();
	void display();
	void remove();
	void update();

	Service* getService() { return this->s; };
	void display_watchlist();
	void add_watchlist();
	void delete_watchlist();

	void menu_admin();
	void menu_user();
	void start();
	void start_user();
	void start_admin();
};