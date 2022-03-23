package model.statements;

import model.PrgState;
import model.adt.IDict;
import model.types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws Exception;
    IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception;
    IStmt deepCopy();
}
