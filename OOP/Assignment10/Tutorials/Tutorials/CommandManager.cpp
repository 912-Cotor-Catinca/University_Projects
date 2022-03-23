#include "CommandManager.h"

void CommandManager::ExecuteCommand(Command* cmd)
{
	this->cmdStackRe = std::stack<Command*>();
	cmd->Execute();
	this->cmdStack.push(cmd);
}

void CommandManager::Undo()
{
	if (this->cmdStack.size() > 0)
	{
		this->cmdStack.top()->Undo();
		this->cmdStackRe.push(this->cmdStack.top());
		this->cmdStack.pop();
	}
}

void CommandManager::Redo()
{
	if (this->cmdStackRe.size() > 0)
	{
		this->cmdStackRe.top()->Redo();
		this->cmdStack.push(this->cmdStackRe.top());
		this->cmdStackRe.pop();
	}
}
