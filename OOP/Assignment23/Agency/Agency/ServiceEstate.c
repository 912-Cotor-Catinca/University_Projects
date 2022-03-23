#include "ServiceEstate.h"
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

int add_estate_srv(Service* s, char type[], char address[], float surface, float price)
{
	Estate e = CreateEstate(type, address, surface, price);
	int a  = add_estate(s->r, e);
	return a;
}

Service* create_service(Repo* repo)
{
	Service* s = (Service*)malloc(sizeof(Service));
	if (s == NULL)
		return NULL;
	s->r = create_repo();
	return s;
}

DynArray* display_all(Service* s)
{
	return get_all(s->r);
}

DynArray* searched_array(Service* s, char* string)
{
	if (string == NULL)
		return display_all(s);

	DynArray* arr = display_all(s);
	DynArray* new_arr = (DynArray*)malloc(sizeof(DynArray));
	new_arr->capacity = arr->capacity;
    new_arr->lenght	= 0;

	new_arr->elem = (TElements*)malloc(new_arr->capacity * sizeof(TElements));

	for(int i = 0; i < len(arr); ++i)
		if (strstr(arr->elem[i].address, string))
		{
			new_arr->elem[new_arr->lenght++] = arr->elem[i];
		}
	if (new_arr == NULL)
		return NULL;
	else
		return new_arr;
}

DynArray* surface_sort(Service* s, float surface)
{
	DynArray* arr = display_all(s);
	DynArray* new_arr = (DynArray*)malloc(sizeof(DynArray));
	new_arr->capacity = arr->capacity;
	new_arr->lenght = 0;

	new_arr->elem = (TElements*)malloc(new_arr->capacity * sizeof(TElements));

	for (int i = 0; i < len(arr); ++i)
		if (arr->elem[i].surface == surface)
		{
			new_arr->elem[new_arr->lenght++] = arr->elem[i];
		}
	if (new_arr == NULL)
		return NULL;
	else
		return new_arr;

}

DynArray* surfacesorted(Service* s, float surface)
{
	Estate aux;
	DynArray* arr = surface_sort(s, surface);
	if (arr == NULL)
		return NULL;
	for (int i = 1; i < len(arr); ++i)
		for (int j = 0; j < len(arr) - i; ++j)
			if (arr->elem[j].price > arr->elem[j + 1].price)
			{
				aux = arr->elem[j];
				arr->elem[j] = arr->elem[j + 1];
				arr->elem[j + 1] = aux;
			}
	return arr;

}

DynArray* by_type(Service* s, char* type)
{
	if (type == NULL)
		return display_all(s);

	DynArray* arr = display_all(s);
	DynArray* new_arr = (DynArray*)malloc(sizeof(DynArray));
	new_arr->capacity = arr->capacity;
	new_arr->lenght = 0;

	new_arr->elem = (TElements*)malloc(new_arr->capacity * sizeof(TElements));

	for (int i = 0; i < len(arr); ++i)
		if (strcmp(arr->elem[i].type, type) == 0)
		{
			new_arr->elem[new_arr->lenght++] = arr->elem[i];
		}
	if (new_arr == NULL)
		return NULL;
	else
		return new_arr;
}

DynArray* greater_than(Service* s, char* type, float value)
{
	DynArray* arr = by_type(s, type);
	DynArray* new_arr = (DynArray*)malloc(sizeof(DynArray));
	new_arr->capacity = arr->capacity;
	new_arr->lenght = 0;

	new_arr->elem = (TElements*)malloc(new_arr->capacity * sizeof(TElements));
	if (arr == NULL)
		return NULL;
	for (int i = 0; i < len(arr); ++i)
		if (arr->elem[i].surface > value)
			new_arr->elem[new_arr->lenght++] = arr->elem[i];
	destroy(arr);
	return new_arr;
}

DynArray* sorted_array(Service* s, char* string)
{
	Estate aux;
	DynArray* arr = searched_array(s, string);
	if (arr == NULL)
		return NULL;
	for (int i = 1; i < len(arr); ++i)
		for(int j = 0; j < len(arr) - i; ++j)
			if(arr->elem[j].price > arr->elem[j+1].price)
			{
				aux = arr->elem[j];
				arr->elem[j] = arr->elem[j+1];
				arr->elem[j + 1] = aux;
			}
	return arr;
	

}

bool delete_srv(Service* s, char* address)
{
	bool f = delete_estate(s->r, address);
	return f;

}

void update_srv(Service* s, char type[], char address[], float surface, float price)
{
	update_estate(s->r, address, type, surface, price);
}

void destroy_srv(Service* s)
{
	//destroy_repo(s->r);
	destroy(s->r->estates);
	free(s->r);
	free(s);
}

