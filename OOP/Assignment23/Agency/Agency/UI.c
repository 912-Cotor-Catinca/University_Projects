#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "UI.h"
#include "Validator.h"


void add_estate_ui(UI* ui)
{
	char type[15], address[50];
	float surface, price;
	char c;
	printf("Type: ");
	scanf("%s", &type);

	printf("Address: ");
	scanf("%c", &c);
	fgets(address, sizeof(address), stdin);
	address[strlen(address) - 1] = '\0';
	//scanf("%s", &address);

	printf("Surface: ");
	scanf("%f", &surface);

	printf("Price: ");
	scanf("%f", &price);

	Estate e = CreateEstate(type, address, surface, price);
	
	if (ValidateSurfAndPrice(surface) == 1 && ValidateSurfAndPrice(price) == 1 && ValidateType(type) == 1)
	{
		int a = add_estate_srv(ui->s, type, address, surface, price);
		MAtrixAdd(ui->undo, ui->s->r);
		if (a == -1)
			printf("the address exist");
	}
	else
		printf("Wrong input!");
	
}

void delete_estate_ui(UI* ui)
{
	char address[50];
	printf("the address: ");
	scanf("%s", &address);
	bool f = delete_srv(ui->s, address);
	MAtrixAdd(ui->undo, ui->s->r);
	if (f == false)
		printf("The given address does not exists!\n");
}

void update_ui(UI* ui)
{
	char type[15], address[50];
	float surface, price;
	
	printf("Address: ");
	scanf("%s", &address);

	printf("Type: ");
	scanf("%s", &type);

	printf("Surface: ");
	scanf("%f", &surface);

	printf("Price: ");
	scanf("%f", &price);
	if (ValidateSurfAndPrice(surface) == 1 && ValidateSurfAndPrice(price) == 1 && ValidateType(type) == 1)
	{
		update_srv(ui->s, type, address, surface, price);
		MAtrixAdd(ui->undo, ui->s->r);
	}
	else
		printf("Wrong Values");
}

UI* create_ui(Service* service)
{
	UI* ui = (UI*)malloc(sizeof(UI));
	if (ui == NULL)
		return NULL;
	ui->s = create_service(service->r);
	ui->undo = createUndo();
	init_array(ui);
	return ui;
}

void display_ui(UI* ui)
{
	
	DynArray* arr = display_all(ui->s);
	for (int i = 0; i < arr->lenght; ++i)
	{
		char s[1001];
		toString(arr->elem[i], s);
		puts(s);
		strcpy(s, "");
	}

	//destroy(arr);
}

void display_by_type(UI* ui)
{
	DynArray* arr;
	char type[15];
	printf("The type: ");
	scanf("%s", &type);

	float surface;
	printf("The value: ");
	scanf("%f", &surface);

	arr = greater_than(ui->s, type, surface);
	for (int i = 0; i < arr->lenght; ++i)
	{
		char s[1001];
		toString(arr->elem[i], s);
		puts(s);
		strcpy(s, "");
	}
	destroy(arr);
}

void display_surface(UI* ui)
{
	DynArray* arr;
	float surface;
	printf("The surface: ");
	scanf("%f", &surface);
	arr = surfacesorted(ui->s, surface);
	for (int i = 0; i < arr->lenght; ++i)
	{
		char s[1001];
		toString(arr->elem[i], s);
		puts(s);
		strcpy(s, "");
	}
	destroy(arr);
}

void display_sort(UI* ui)
{
	DynArray* arr;
	char string[50];
	char c;
	printf("The string: ");
	//
	scanf("%c", &c);
	fgets(string, sizeof(string), stdin);
	if (string == '\n')
		arr = display_all(ui->s);
	else
	{
		string[strlen(string) - 1] = '\0';
		arr = sorted_array(ui->s, string);
	}
		

	for (int i = 0; i < arr->lenght; ++i)
	{
		char s[1001];
		toString(arr->elem[i], s);
		puts(s);
		strcpy(s, "");
	}
	destroy(arr);
}

void UndoOp(UI* ui)
{
	int a = undo(ui->undo, ui->s->r);
	if (a == -1)
		printf("Nothing to undo");
}

void RedoOP(UI* ui)
{
	int a = redo(ui->undo, ui->s->r);
	if (a == -1)
		printf("Nothing to redo");
}

void menu(UI* ui)
{
	printf("Press 1 to add an estate\n");
	printf("Press 2 to delete an estate\n");
	printf("Press 3 to update an estate\n");
	printf("Press 4 to display all estates\n");
	printf("Press 5 to display all estates whose address contains a given string\n");
	printf("Press 6 to see all estates of a given type, having the surface greater than a user provided value.\n");
	printf("Press 8 to  display all offers, sorted ascending by price for a given surface.\n");
	printf("Press 9 to undo.\n");
	printf("Press 10 to redo.\n");
	printf("Press 7 to exit the application\n");
}

void start_app(UI* ui)
{
	bool done = false;
	while (!done)
	{
		char cmd[20];
		printf("\nEnter a number: ");
		scanf("%s", &cmd);
		int nr = atoi(cmd);
		if (nr == 0)
			printf("Bad command!");
		else if (nr == 1)
			add_estate_ui(ui);
		else if (nr == 2)
			delete_estate_ui(ui);
		else if (nr == 4)
			display_ui(ui);
		else if (nr == 3)
			update_ui(ui);
		else if (nr == 5)
			display_sort(ui);
		else if (nr == 6)
			display_by_type(ui);
		else if (nr == 8)
			display_surface(ui);
		else if (nr == 9)
			UndoOp(ui);
		else if (nr == 10)
			RedoOP(ui);
		else if (nr == 7)
		{
			printf("Bye!\n");
			done = true;
			return;
		}
		else
			printf("Bad command!");
	}
}

void destroy_ui(UI* ui)
{
	destroy_srv(ui->s);
	ui->s = NULL;
	destroy_undo(ui->undo);

	free(ui);
}

void init_array(UI* ui)
{
	add_estate_srv(ui->s, "house", "Str.Eroilor,nr6", 1234.34, 50000);
	add_estate_srv(ui->s, "apartament", "Str.Lucian Blaga", 120, 4000);
	add_estate_srv(ui->s, "apartament", "Str.Goga,nr.7", 120, 5800);
	add_estate_srv(ui->s, "penthouse", "Str.Garii", 300, 7000);
	add_estate_srv(ui->s, "penthouse", "Str.Voronet", 190, 35000);
	add_estate_srv(ui->s, "house", "Bulevardul Muncii", 250, 12800);
	add_estate_srv(ui->s, "apartament", "Bulevardul 1Decembrie", 500, 500000);
	add_estate_srv(ui->s, "aparatament", "Aleea Snagov", 80, 6300);
	add_estate_srv(ui->s, "house", "Str.Liviu Rebreanu", 200, 450000);
	add_estate_srv(ui->s, "penthouse", "Str.Pacii", 189, 89000);
	MAtrixAdd(ui->undo, ui->s->r);
}