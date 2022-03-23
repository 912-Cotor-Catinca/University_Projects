package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.type.IType;

public class NOPStmt implements Model.stmt.IStmt {
    @Override
    public PrgState execute(PrgState state) throws Exception {
        return null;
    }

    @Override
    public String toString(){
        return "no operation";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        return null;
    }
}
