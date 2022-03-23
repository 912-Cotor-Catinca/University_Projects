#pragma once
#include <stack>
#include "Command.h"
class CommandManager
{
private:
	std::stack<Command*> cmdStack;
	std::stack<Command*> cmdStackRe;
public:
	void ExecuteCommand(Command* cmd);
	void Undo();
	void Redo();
};

