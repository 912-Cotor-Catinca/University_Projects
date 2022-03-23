package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.type.BoolType;
import Model.type.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class WhileStmt implements Model.stmt.IStmt {
    private final Exp exp;
    private final IStmt stmt;

    public WhileStmt(Exp exp, Model.stmt.IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<Model.stmt.IStmt> stack = state.getStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<Integer, IValue> heap = state.getHeapTable();
        IValue val = this.exp.eval(symTbl, heap);

        if((val.getType()).equals(new BoolType())){
            BoolValue cond = (BoolValue) val;
            if(cond.getVal())
            {
                //Model.stmt.IStmt copyWhile = new Model.stmt.WhileStmt(exp, stmt);
                stack.push(this);
                stack.push(stmt);
            }
            return null;
        }else throw new DeclaredExceptions("conditional exp is not a boolean");

    }

    @Override
    public String toString(){
        return "while (" + this.exp.toString() + ") { " + this.stmt.toString() + "}";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type_exp = exp.typeCheck(typeEnv);
        if(type_exp.equals(new BoolType())){
            stmt.typeCheck(typeEnv.copy());
            return typeEnv;
        }else throw new DeclaredExceptions("The condition of WHILE has not the type bool");
    }
}
