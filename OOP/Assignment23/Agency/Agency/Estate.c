#define _CRT_SECURE_NO_WARNINGS
#include "RealEstate.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

 
Estate CreateEstate(char type[], char address[], float surface, float price)
{
	
	Estate e;
	strcpy(e.type, type);
	strcpy(e.address, address);
	e.surface = surface;
	e.price = price;

	return e;
	
/*
	Estate* e = (Estate*)malloc(sizeof(Estate));
	e->surface = surface;
	e->price = price;
	e->type = malloc(sizeof(char) * (strlen(type) + 1));
	strcpy(e->type, type);

	e->address = malloc(sizeof(char) * (strlen(address) + 1));
	strcpy(e->address, address);
	return e;
	*/
}

Estate CopyEstate(Estate* e)
{
	return CreateEstate(e->type, e->address, e->surface, e->price);
}


char* GetType(Estate* e)
{
	return e->type;
}

char* GetAddress(Estate* e)
{
	return e->type;
}

float GetSurface(Estate* e)
{
	return e->surface;
}

float GetPrice(Estate* e)
{
	return e->price;
}

void toString(Estate e, char s[])
{
	sprintf(s, "Estate : %s; Address: %s; Surface: %.2f; Price: %.2f", e.type, e.address, e.surface, e.price);
}

