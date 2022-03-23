#include <iostream>
#include "MasterC.h"
#include "DynArray.h"
#include "Repo.h"
#include "Service.h"
#include "UI.h"
#include "Tests.h"

using namespace std;

int main()
{
	UI ui;
	ui.start();
	
	Test t = Test();
	t.Test_Array();
	t.Test_Domain();
	t.Test_Repo();
	t.Test_Service();
	t.Test_RepoUser();
	t.Test_ServUser();


	return 0;
}