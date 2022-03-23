package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.type.IType;

public class CompStmt implements Model.stmt.IStmt {
    private Model.stmt.IStmt first;
    private Model.stmt.IStmt second;
    public CompStmt(Model.stmt.IStmt first, Model.stmt.IStmt snd){
        this.first = first;
        this.second = snd;
    }

    @Override
    public String toString(){
        return "(" + this.first.toString() + ";" + this.second.toString()+")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public PrgState execute(PrgState state) {
        IStack<Model.stmt.IStmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    public Model.stmt.IStmt getFirst() {
        return this.first;
    }
    public Model.stmt.IStmt getSecond(){
        return this.second;
    }
}
