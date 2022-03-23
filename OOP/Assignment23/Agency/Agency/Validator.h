#pragma once
#include "RealEstate.h"
/// <summary>
/// Validates the type of an estate.
/// </summary>
/// <param name="type"></param>
/// <returns>1 if the parameters are correct, -1 otherwise</returns>
int ValidateType(char* type);
/// <summary>
/// Validates the address of an estate.
/// </summary>
/// <param name="address"></param>
/// <returns>1 if the parameters are correct, -1 otherwise</returns>
int ValidateAddress(char* address);

/// <summary>
/// Validates the surface and the price of an estate.
/// </summary>
/// <param name="value"></param>
/// <returns>1 if the parameters are correct, -1 otherwise</returns>
int ValidateSurfAndPrice(float value);