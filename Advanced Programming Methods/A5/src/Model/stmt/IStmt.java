package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.type.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws Exception;
    String toString();
    IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception;
}
