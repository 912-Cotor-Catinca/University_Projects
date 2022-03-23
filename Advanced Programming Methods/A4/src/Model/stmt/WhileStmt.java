package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.value.BoolValue;
import Model.value.IValue;

public class WhileStmt implements IStmt{
    private final Exp exp;
    private final IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<Integer, IValue> heap = state.getHeapTable();
        IValue val = this.exp.eval(symTbl, heap);

        if((val.getType()).equals(new BoolType())){
            BoolValue cond = (BoolValue) val;
            if(cond.getVal())
            {
                //IStmt copyWhile = new WhileStmt(exp, stmt);
                stack.push(this);
                stack.push(stmt);
            }
            return state;
        }else throw new DeclaredExceptions("conditional exp is not a boolean");

    }

    @Override
    public String toString(){
        return "while (" + this.exp.toString() + ") { " + this.stmt.toString() + "}";
    }
}
