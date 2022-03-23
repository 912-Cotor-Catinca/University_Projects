package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException;
    IStmt deepCopy();
}
