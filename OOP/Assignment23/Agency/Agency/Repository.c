#include "Repository.h"
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

Repo* create_repo()
{
	Repo* r = (Repo*)malloc(sizeof(Repo));
	if (r == NULL)
		return NULL;
	r->estates = createArray(100);
	//DynArray* arr = createArray(10);
	return r;
}

void destroy_repo(Repo* r)
{
	//free(r->estates->elem);
	free(r);
}

int add_estate(Repo*r, Estate e)
{
	int pos = find_address(r, GetAddress(&e));
	if (pos == -1)
		add(r->estates, e);
	else
		return -1;
	return 1;
}

int find_address(Repo*r, char* address)
{
	for (int i = 0; i < r->estates->lenght; ++i)
		if (strcmp(r->estates->elem[i].address, address) == 0)
			return i;
	return -1;
}

DynArray* get_all(Repo* r)
{
	return display(r->estates);
}

bool delete_estate(Repo*r, char* address) {
	int pos = find_address(r, address);
	if (pos != -1)
	{
		delete_elem(r->estates, r->estates->elem[pos]);
		return true;
	}
	else
		return false;
}

void update_estate(Repo* r,char* address, char* type, float surface, float price)
{
	int pos = find_address(r, address);
	if (pos != 1)
	{
		update(r->estates, r->estates->elem[pos], type, surface, price);
	}
	else return;
}

void copy_repo(Repo* repo1, Repo* repo2)
{
	DeepCopyArray(repo1->estates, repo2->estates);
}