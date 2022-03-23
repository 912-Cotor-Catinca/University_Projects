#pragma once
#include <string>
#include <exception>
#include "MasterC.h"

class Validator {
public:
	Validator() {};
	int Validate_Pres(std::string pres);
	int Validate_Title(std::string title);
	int Validate_Duration(std::string duration);
	void Validate_All(const MasterC& c);
};

class ValidationException {
private:
	std::string message;

public:
	ValidationException(std::string _message);
	std::string getMessage() const;
};