#include "Validator.h"

int Validator::Validate_Pres(std::string pres)
{
	if (pres != "")
		return 1;
	return -1;
}
int Validator::Validate_Title(std::string title)
{
	if (title != "")
		return 1;
	return -1;
}

int Validator::Validate_Duration(std::string duration)
{
	std::string token1 = duration.substr(0, duration.find(":"));
	std::string token2 = duration.substr(duration.find(":") + 1, duration.length());
	int hours = stoi(token1);
	int minute = stoi(token2);
	if (hours < 25 && hours > 0 && minute < 61 && minute > 0)
		return 1;
	return -1;
}

void Validator::Validate_All(const MasterC& c)
{
	string errors;
	if (c.getTitle().size() == 0)
		errors += string("The tutorial's title is invalid \n");
	if (c.getPresenter().size() == 0)
		errors += string("The tutorial's presenter is invalid \n");
	if (Validate_Duration(c.getDuration()) != 1)
		errors += string("The tutorial's duration is invalid \n");
	if(c.getLink().size() < 3)
		errors += string("The tutorial's link is invalid\n");
	if(c.getLikes() < 0)
		errors += string("The tutorial's number of likes is invalid\n");

	if (errors.size() > 0)
		throw ValidationException(errors);
}

ValidationException::ValidationException(std::string _message) : message{ _message }
{}

std::string ValidationException::getMessage() const
{
	return this->message;
}