#include "Tests.h"
#include "Validator.h"
#include "DynArray.h"
#include "UI.h"
#include "ServiceEstate.h"
#include "Repository.h"
#include "UndoRedo.h"
#include <assert.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include<stdio.h>


void test_domain() {
	char type[] = "apartrament";
	char address[] = "Str.Oct Goga";
	float surface = 80;
	float price = 4000.5;
	Estate e = CreateEstate(type, address, surface, price);
	assert(strcmp(e.type, type) == 0);
	assert(strcmp(e.address, address) == 0);
	assert(e.price == price);
	assert(e.surface == surface);
	
	//free(type);
	//free(address);

}

void test_val() 
{
	char type[] = "apartament";
	char address[] = "Str.Oct Goga";
	float surface = 80;
	float price = 4000.5;
	Estate e = CreateEstate(type, address, surface, price);
	assert(ValidateType(e.type) == 1);
	assert(ValidateAddress(e.address) == 1);
	assert(ValidateSurfAndPrice(e.surface) == 1);
	assert(ValidateSurfAndPrice(e.price) == 1);
}

void test_add()
{
	DynArray* arr = createArray(5);
	assert(arr->lenght == 0);
	char type[] = "apartrament";
	char address[] = "Str.Oct Goga";
	float surface = 80;
	float price = 4000.5;
	Estate e = CreateEstate("apartrament", "Str.Oct Goga", surface, price);
	Repo* r = create_repo();
	add(arr, e);
	assert(arr->lenght == 1);

	
	char type1[] = "house";
	float surface1 = 800;
	float price1 = 400.5;
	update(arr, e, type1, surface1, price1);
	

	destroy(arr);
	
	
	destroy(r->estates);
	destroy_repo(r);

}

void test_repo()
{
	
	Repo* r = create_repo();
	Estate e = CreateEstate("house", "asdf", 12, 123);
	add_estate(r, e);
	assert(r->estates->lenght == 1);
	DynArray* arr = get_all(r);
	//for (int i = 0; i < arr->lenght; ++i)
		//printf("q");
	bool f = delete_estate(r, "asdf");
	destroy(r->estates);
	destroy_repo(r);
	//DestroyEstate(&e);
	test_add();
}

void test_service()
{
	Repo* r = create_repo();

	Service* s = create_service(r);
	add_estate_srv(s, "adxf", "asdas", 12, 3);
	add_estate_srv(s, "adxfsa", "asdsas", 12, 3);
	bool f = delete_srv(s, "asdas");
	assert(len(s->r->estates) == 1);
	update_srv(s, "aa", "asdsas", 122, 45);
	
	assert(GetSurface(&s->r->estates->elem[0]) == 122);
	//b) functionality
	DynArray* arr = sorted_array(s, "a");
	assert(len(arr) == 1);
	destroy(arr);
	//c)functionality
	DynArray* arr1 = greater_than(s, "adxfsa", 2);
	assert(len(arr1) == 0);
	destroy(arr1);

	//given last lab
	DynArray* arr2 = surfacesorted(s, 122);
	assert(len(arr2) == 1);
	destroy(arr2);

	destroy(r->estates);
	destroy_repo(r);
	destroy_srv(s);
}

void test_undo()
{
	Repo* r = create_repo();
	Service* s = create_service(r);
	
	Undo* undo1 = createUndo();
	add_estate_srv(s, "house", "asdf", 12, 123);
	MAtrixAdd(undo1, s->r);
	add_estate_srv(s, "apartrament", "Str.Oct Goga", 4, 45);
	assert(len(s->r->estates) == 2);
	MAtrixAdd(undo1, s->r);
	assert(GetIndex(undo1) == 1);
	assert(GetLen(undo1) == 2);
	assert(GetCap(undo1) == 100);
	int a = undo(undo1, s->r);
	assert(a == 1);
	assert(s->r->estates->elem[0].price == 123);
	assert(s->r->estates->elem[0].surface == 12);
	
	destroy(r->estates);
	destroy_repo(r);
	destroy_undo(undo1);
	destroy_srv(s);
}

void test_all()
{
	test_domain();
	test_val();
	test_repo();
	test_service();
	test_undo();
}