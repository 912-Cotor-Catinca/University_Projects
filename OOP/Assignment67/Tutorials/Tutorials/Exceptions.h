#pragma once
#include <exception>
#include<string>

class Exception : public std::exception
{
private:
	std::string message;
public:
	Exception(const std::string& s) : message{ s } {};
	const char* what() const throw() override { return this->message.c_str(); }
};