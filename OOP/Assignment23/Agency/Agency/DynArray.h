#pragma once
#include "RealEstate.h"
#include <stdbool.h>

typedef Estate TElements;

typedef struct 
{
	TElements* elem;
	int lenght;
	int capacity;
}DynArray;

/// <summary>
/// Creates a dynamic array of generic elements, with a given capacity.
/// </summary>
/// <param name="capacity">Integer, maximum capacity for the dynamic array.</param>
/// <returns>A pointer the the created dynamic array.</returns>
DynArray* createArray(int capacity);

/// <summary>
/// Destroys the dynamic array.
/// </summary>
/// <param name="arr">The dynamic array to be destoyed.</param>
/// <returns>A pointer the the created dynamic array.</returns>
void destroy(DynArray* arr);

/// <summary>
/// Adds a generic element to the dynamic array.
/// </summary>
/// <param name="arr">The dynamic array.</param>
/// <param name="p">The estate to be added.</param>
void add(DynArray* arr, TElements e);

/// <summary>
/// Removes one occurence of an element from a dynamic array.
/// </summary>
/// <param name="arr">The dynamic array.</param>
/// <param name="e">The estate to be deleted.</param>
/// <returns>returns true if an element was removed, false otherwise (if e was not part of the array)</returns>
bool delete_elem(DynArray* arr, TElements e);

/// <summary>
/// Updates an existing element in the dynamic array.
/// </summary>
/// <param name="arr">The dynamic array.</param>
/// <param name="e">The estate to be updated</param>
/// <param name="new_type">The new value for type</param>
/// <param name="new_surf">The new value for surface</param>
/// <param name="new_price">The new value for price</param>
void update(DynArray* arr, TElements e, char new_type[], float new_surf, float new_price);

/// <summary>
/// Returns all the elements from the dynamic array
/// </summary>
/// <param name="arr">The dynamic array.</param>
/// <returns></returns>
DynArray* display(DynArray* arr);

/// <summary>
/// Returns the lenght of the dynamic array
/// </summary>
/// <param name="arr">The dynamic array.</param>
/// <returns></returns>
int len(DynArray* arr);

///<summary>
///Deep copy of the dynamic array.
/// <summary>
/// /// <param name="arr1">The dynamic array into which elements are copied.</param>
/// /// <param name="arr2">The dynamic array from which elements are copied.</param>
void DeepCopyArray(DynArray* arr1, DynArray* arr2);