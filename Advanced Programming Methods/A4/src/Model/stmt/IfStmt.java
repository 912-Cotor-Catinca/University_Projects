package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.*;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class IfStmt implements Model.stmt.IStmt {
    private Exp exp;
    private Model.stmt.IStmt thenS;
    private Model.stmt.IStmt elseS;

    public IfStmt(Exp exp, Model.stmt.IStmt thenS, Model.stmt.IStmt elseS){
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<Model.stmt.IStmt> stack = state.getStack();
        IList<IValue> out = state.getOut();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<Integer, IValue> heap = state.getHeapTable();
        IValue val = this.exp.eval(symTbl, heap);
        IType cond = val.getType();
        if (!val.getType().equals(new BoolType())) {
            throw new DeclaredExceptions("Conditional expr is not boolean");
        }
        else {
            BoolValue v1;
            v1 = (BoolValue) val;
            boolean b1 = v1.getVal();
            if (b1) {
                stack.push(this.thenS);
            } else
                stack.push(this.elseS);
        }
        return state;
    }

    @Override
    public String toString(){
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString()  +")ELSE("+elseS.toString()+"))";
    }
}
