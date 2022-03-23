#pragma once
#include "ServiceEstate.h"
#include "UndoRedo.h"
#include <stdbool.h>

typedef struct {
	Service* s;
	Undo* undo;

}UI;

UI* create_ui(Service* service);
void add_estate_ui(UI* ui);
void delete_estate_ui(UI* ui);
void menu(UI* ui);
void start_app(UI* ui);
void display_ui(UI* ui);
void update_ui(UI* ui);
void display_sort(UI* ui);
void display_by_type(UI* ui);
void destroy_ui(UI* ui);
void init_array(UI* ui);
void display_surface(UI* ui);
void UndoOp(UI* ui);
void RedoOP(UI* ui);