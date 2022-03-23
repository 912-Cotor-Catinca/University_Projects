package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IList;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.value.IValue;

public class PrintStmt implements Model.stmt.IStmt {
    private Exp exp;
    public PrintStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<Model.stmt.IStmt> stack = state.getStack();
        IList<IValue> out = state.getOut();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<Integer, IValue> heap = state.getHeapTable();
        IValue val = this.exp.eval(symTbl, heap);
        out.add(val);
        state.setOut(out);
        return state;
    }
    @Override
    public String toString(){
        return "print(" +exp.toString()+")";
    }
}
