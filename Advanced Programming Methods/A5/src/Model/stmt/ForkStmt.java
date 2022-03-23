package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MyStack;
import Model.type.IType;
import Model.value.IValue;


public class ForkStmt implements IStmt{
    private final IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = new MyStack<>();
        stack.push(stmt);
        IDict<String, IValue> symTbl = state.getSymTable().copy();
        return new PrgState(stack, symTbl,state.getOut(), stmt, state.getFileTable(), state.getHeapTable());
    }

    @Override
    public String toString(){
        return String.format("Fork(%s", stmt);
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        stmt.typeCheck(typeEnv.copy());
        return typeEnv;
    }
}
