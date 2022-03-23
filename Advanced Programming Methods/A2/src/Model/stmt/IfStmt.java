package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

import java.util.Objects;

public class IfStmt implements IStmt{
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS){
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStack();
        IList<IValue> out = state.getOut();
        IDict<String, IValue> symTbl = state.getSymTable();
        IValue val = this.exp.eval(symTbl);
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
