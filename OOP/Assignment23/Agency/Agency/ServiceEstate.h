#pragma once
#include "Repository.h"

#include <stdbool.h>

typedef struct {
	Repo* r;

}Service;

/// <summary>
/// Service Constructor
/// </summary>
/// <param name="repo"></param>
/// <returns></returns>
Service* create_service(Repo* repo);
/// <summary>
/// Adds an estate in the repo.
/// </summary>
/// <param name="s"></param>
/// <param name="type"></param>
/// <param name="address"></param>
/// <param name="surface"></param>
/// <param name="price"></param>
/// <returns>1 -> the given estate does not exist; -1-> otherwise.</returns>
int add_estate_srv(Service* s, char type[], char address[], float surface, float price);
/// <summary>
/// Returns the dynamic array of all offers.
/// </summary>
/// <param name="s"></param>
/// <returns></returns>
DynArray* display_all(Service* s);
/// <summary>
/// Deletes an estate from the repo.
/// </summary>
/// <param name="s"></param>
/// <param name="address"></param>
/// <returns>Returns false if the address does not exist. True->otherwise</returns>
bool delete_srv(Service* s, char* address);
/// <summary>
/// Updates an estate's atributes.
/// </summary>
/// <param name="s">The service</param>
/// <param name="type">The new type</param>
/// <param name="surface">The new surface</param>
/// <param name="price">The new price</param>
/// <param name="address">The address</param>
void update_srv(Service* s, char type[], char address[], float surface, float price);
/// <summary>
/// Sorts an array of estates whose addresses contains a given string 
/// </summary>
/// <param name="s"></param>
/// <param name="string"></param>
/// <returns>The array sorted</returns>
DynArray* sorted_array(Service* s, char* string);
/// <summary>
/// Returns the array of estate in whici each estate's address contains the [string]
/// </summary>
/// <param name="s"></param>
/// <param name="string"></param>
/// <returns></returns>
DynArray* searched_array(Service* s, char* string);
/// <summary>
/// The service Destructor
/// </summary>
/// <param name="s"></param>
void destroy_srv(Service* s);
/// <summary>
/// Returns the array of estates of a given type, having the surface greater than a user provided value.
/// </summary>
/// <param name="s"></param>
/// <param name="type"></param>
/// <param name="value"></param>
/// <returns></returns>
DynArray* greater_than(Service* s, char* type, float value);
/// <summary>
/// Returns the estates filtered by type.
/// </summary>
/// <param name="s"></param>
/// <param name="type"></param>
/// <returns></returns>
DynArray* by_type(Service* s, char* type);
/// <summary>
/// Sorts the estates of a given surface by price.
/// </summary>
/// <param name="s"></param>
/// <param name="surface"></param>
/// <returns></returns>
DynArray* surfacesorted(Service* s, float surface);
/// <summary>
/// Returns the estates filtered by surface.
/// </summary>
/// <param name="s"></param>
/// <param name="surface"></param>
/// <returns></returns>
DynArray* surface_sort(Service* s, float surface);


