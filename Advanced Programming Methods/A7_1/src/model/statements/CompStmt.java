package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.types.IType;

public class CompStmt implements IStmt {
    IStmt first, second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) {
        IStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first, second);
    }

    @Override
    public String toString() {
        return String.format("%s; %s", first, second);
    }
}
