#define _CRTDBG_MAP_ALLOC
#include <crtdbg.h>
#include "RealEstate.h"
#include "DynArray.h"
#include "Repository.h"
#include "ServiceEstate.h"
#include "UI.h"
#include "Tests.h"
#include <stdio.h>
#include <stdlib.h>

int main()
{
	//test_add();
	test_all();
	
	
	Repo* r = create_repo();

	Service* s = create_service(r);
	UI* ui = create_ui(s);
	menu(ui);
	
	start_app(ui);
	destroy(r->estates);
	destroy_repo(r);
	
	destroy_srv(s);
	destroy_ui(ui);
	
	int a = _CrtDumpMemoryLeaks();
	printf("If a == 0 -> there are no memmory leaks! a = %d", a);
	

	return 0;
}