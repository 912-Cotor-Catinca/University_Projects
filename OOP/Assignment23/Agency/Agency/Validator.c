#include "Validator.h"
#include <string.h>

int ValidateType(char* type)
{
	if (strcmp(type, "house") != 0 && strcmp(type, "apartament") != 0 && strcmp(type, "penthouse") != 0)
		return -1;
	return 1;
}
int ValidateAddress(char* address)
{
	if (address == "")
		return -1;
	return 1;
}
int ValidateSurfAndPrice(float value)
{
	if (value < 0)
		return -1;
	return 1;
}