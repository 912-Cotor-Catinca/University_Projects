#pragma once
#include "DynArray.h"
#include <stdbool.h>


typedef struct {
	DynArray* estates;
}Repo;

/// <summary>
/// Repository Constructor
/// </summary>
/// <returns></returns>
Repo* create_repo();

/// <summary>
/// Adds an estate into the repo if this estate does not exist yet.
/// </summary>
/// <param name="r">The reposotory</param>
/// <param name="e">The estate to be added</param>
/// <returns>1 -> the given estate does not exist; -1-> otherwise.</returns>
int add_estate(Repo* r, Estate e);
/// <summary>
/// Deletes an estate from the repository.
/// </summary>
/// <param name="r">The reposotory</param>
/// <param name="address">The estate to be deleted</param>
/// <returns>Returns false if the address does not exist. True->otherwise</returns>
bool delete_estate(Repo*r, char* address);
/// <summary>
/// Updates an estate's atributes.
/// </summary>
/// <param name="r">The address</param>
/// <param name="address">The new type</param>
/// <param name="type">The new surface</param>
/// <param name="surface">The new price</param>
/// <param name="price"></param>
void update_estate(Repo* r, char* address, char* type, float surface, float price);
/// <summary>
/// Returns a shallow copy of the repository.
/// </summary>
/// <param name="r"></param>
/// <returns></returns>
DynArray* get_all(Repo* r);
/// <summary>
/// Search for the address.
/// </summary>
/// <param name="r">The repository</param>
/// <param name="address">The address which is searched by.</param>
/// <returns>The position in the repository if the address exist, -1 otherwise</returns>
int find_address(Repo*r, char* address);
/// <summary>
/// Repository Destructor
/// </summary>
/// <param name="r"></param>
void destroy_repo(Repo* r);
///Deep copy of the repository.
void copy_repo(Repo* repo1, Repo* repo2);