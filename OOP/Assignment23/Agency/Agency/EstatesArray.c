#define _CRT_SECURE_NO_WARNINGS
#include "DynArray.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <assert.h>

DynArray* createArray(int capacity) {
	DynArray* arr = (DynArray*)malloc(sizeof(DynArray));
	if (arr == NULL)
	{
		return NULL;
	}

	arr->capacity = capacity;
	arr->lenght = 0;

	arr->elem = (TElements*)malloc(capacity * sizeof(TElements));
	if (arr->elem == NULL)
		return NULL;

	return arr;
}

void destroy(DynArray* arr) {
	if (arr == NULL)
		return;

	free(arr->elem);
	arr->elem = NULL;

	free(arr);
}

void resize(DynArray* arr) {
	arr->capacity = arr->capacity * 2;
	arr->elem = realloc(arr->elem, arr->capacity*sizeof(TElements));
}

void add(DynArray* arr, TElements e) {
	if (arr == NULL)
		return;
	if (arr->elem == NULL)
		return;
	if (arr->capacity == arr->lenght)
		resize(arr);
	arr->elem[arr->lenght] = e;
	arr->lenght++;
}

void update(DynArray* arr, TElements e, char new_type[], float new_surf, float new_price)
{
	if (arr == NULL)
		return;
	if (arr->elem == NULL)
		return;
	int pos = 0;
	bool found = false;
	while (pos < len(arr) && found == false)
	{
		if (strcmp(arr->elem[pos].address, e.address) == 0)
		{
			found = true;
		}

		else
			pos++;
	}
	if (found == false)
	{
		return;
	}
	else
	{
		arr->elem[pos].price = new_price;
		arr->elem[pos].surface = new_surf;
		strcpy(arr->elem[pos].type, new_type);
		
	}
	
}

bool delete_elem(DynArray* arr, TElements e) {
	int pos = 0;
	bool found = false;
	while (pos < len(arr) && found == false)
	{
		if (strcmp(arr->elem[pos].address, e.address) == 0)
		{
			found = true;
		}
			
		else
			pos++;
	}
	if (found == false)
	{
		printf("da");
		return false;
	}
		
	else {
		arr->elem[pos] = arr->elem[arr->lenght - 1];
		arr->lenght--;
		return true;
	}

}

DynArray* display(DynArray* arr) {
	return arr;
}

int len(DynArray* arr)
{
	return arr->lenght;
}

void DeepCopyArray(DynArray* arr1, DynArray* arr2)
{
	for (int i = 0; i < len(arr2); ++i)
	{
		strcpy(arr1->elem[i].type, arr2->elem[i].type);
		strcpy(arr1->elem[i].address, arr2->elem[i].address);
		arr1->elem[i].surface = arr2->elem[i].surface;
		arr1->elem[i].price = arr2->elem[i].price;
	}
}