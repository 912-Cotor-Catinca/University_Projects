package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.adt.MyDict;
import model.adt.MyStack;
import model.types.IType;
import model.values.IValue;

import java.util.Map;

public class ForkStmt implements IStmt {
    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> newSymTable = new MyDict<>();
        for (Map.Entry<String, IValue> entry: state.getSymTable().getContent().entrySet()) {
            newSymTable.add(entry.getKey(), entry.getValue().deepCopy());
        }
        IStack<IStmt> stack = new MyStack<>();
        stack.push(stmt);
        PrgState newPrgState = new PrgState(stack, newSymTable, state.getFileTable(), state.getHeapTable(), state.getOut());
        newPrgState.setID();
        return newPrgState;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        stmt.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt);
    }

    @Override
    public String toString() {
        return String.format("fork(%s)", stmt);
    }
}
