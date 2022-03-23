#pragma once

typedef struct {
	char type[15];
	char address[50];
	float surface;
	float price;
} Estate;

/// <summary>
/// Estate Constructor
/// </summary>
/// <param name="type">The estate's type. Char string</param>
/// <param name="address">The estate's address. Char string</param>
/// <param name="surface">The estate's surface. Integer</param>
/// <param name="price">The estate's price. Integer</param>
/// <returns>Returns an estate object</returns>
Estate CreateEstate(char type[], char address[], float surface, float price);
/// <summary>
/// Returns copy of the estate
/// </summary>
/// <param name="e"></param>
/// <returns></returns>
Estate CopyEstate(Estate* e);

/// <summary>
/// Gets the type of an estate.
/// </summary>
/// <param name="e"></param>
/// <returns></returns>
char* GetType(Estate* e);
/// <summary>
/// Gets the Address of an estate.
/// </summary>
/// <param name="e"></param>
/// <returns></returns>
char* GetAddress(Estate* e);
/// <summary>
/// Gets the surface of an estate.
/// </summary>
/// <param name="e"></param>
/// <returns></returns>
float GetSurface(Estate* e);
/// <summary>
/// Gets the price of an estate.
/// </summary>
/// <param name="e"></param>
/// <returns></returns>
float GetPrice(Estate* e);
/// <summary>
/// The way the offers are printed. Very pretty.
/// </summary>
/// <param name="e">The estate</param>
/// <param name="s">The string to be printed</param>
void toString(Estate e, char s[]);
