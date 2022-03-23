package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.types.IType;
import model.values.IValue;

public class VarDecStmt implements IStmt {
    String name;
    IType type;

    public VarDecStmt(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) {
        IDict<String, IValue> symTable = state.getSymTable();
        symTable.add(name, type.getDefault());
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        typeEnv.add(name, type);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDecStmt(name, type);
    }

    @Override
    public String toString() {
        return String.format("%s %s", type.toString(), name);
    }
}
