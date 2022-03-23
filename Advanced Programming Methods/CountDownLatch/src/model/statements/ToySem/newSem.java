package model.statements.ToySem;

import model.PrgState;
import model.adt.IDict;
import model.adt.IHeap;
import model.adt.IToySem;
import model.adt.Triplet;
import model.expressions.IExp;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newSem implements IStmt {
    String var;
    IExp exp1, exp2;
    private Lock lock = new ReentrantLock();
    public newSem(String var, IExp exp1, IExp exp2){
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<IValue> heap = state.getHeapTable();
        IToySem sem = state.getToySem();
        if(symTbl.isDefined(var)) {
            IValue val = this.exp1.eval(symTbl, heap);
            IValue val1 = this.exp2.eval(symTbl, heap);
            Integer num1 = ((IntValue) val).getVal();
            Integer num2 = ((IntValue) val1).getVal();
            lock.lock();
            int freePos = sem.getTSemAddress();
            sem.getSemaphore().add(freePos, new Triplet<>(num1, new ArrayList<>(), num2));
            symTbl.update(var, new IntValue(freePos));
            lock.unlock();
        }else
            throw new Exception("var is not in symbol table");

        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType var_type = typeEnv.lookup(var);
        IType exp_type1 = exp1.typeCheck(typeEnv);
        IType exp_type2 = exp2.typeCheck(typeEnv);
        if(!var_type.equals(new IntType()))
            throw new Exception("variable is not int");
        if(!exp_type1.equals(new IntType()) && !exp_type2.equals(new IntType()))
            throw new Exception("expression is not an int type");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new newSem(var, exp1, exp2);
    }

    @Override
    public String toString() {
        return "newToySemaphore(" + var + "; " + exp1.toString() + "; "
                + exp2.toString() + ")";
    }
}
