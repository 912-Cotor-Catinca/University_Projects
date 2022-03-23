package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.*;
import Model.exp.Exp;
import Model.type.BoolType;
import Model.type.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class IfStmt implements Model.stmt.IStmt {
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp exp, Model.stmt.IStmt thenS, Model.stmt.IStmt elseS){
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        Model.adt.IStack<Model.stmt.IStmt> stack = state.getStack();
        Model.adt.IList<IValue> out = state.getOut();
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
        return null;
    }

    @Override
    public String toString(){
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString()  +")ELSE("+elseS.toString()+"))";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type_exp = exp.typeCheck(typeEnv);
        if(type_exp.equals(new BoolType())){
            thenS.typeCheck(typeEnv.copy());
            elseS.typeCheck(typeEnv.copy());
            return typeEnv;
        }else throw new DeclaredExceptions("The condition of IF has not the type bool");
    }
}
