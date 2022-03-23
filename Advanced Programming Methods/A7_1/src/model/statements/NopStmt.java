package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.types.IType;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
