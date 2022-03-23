package Model.stmt;

import Model.PrgState;
import Model.adt.IStack;
import Model.adt.MyStack;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt second;
    public CompStmt(IStmt first, IStmt snd){
        this.first = first;
        this.second = snd;
    }

    @Override
    public String toString(){
        return "(" + this.first.toString() + ";" + this.second.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) {
        IStack<IStmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return state;
    }

    public IStmt getFirst() {
        return this.first;
    }
    public IStmt getSecond(){
        return this.second;
    }
}
