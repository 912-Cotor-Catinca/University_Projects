package model.statements.Semaphore;

import model.PrgState;
import model.adt.IDict;
import model.adt.IHeap;
import model.adt.Pair;
import model.expressions.IExp;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class createSemaphore implements IStmt {
    String var;
    IExp exp;
    private static Lock lock = new ReentrantLock();
    public createSemaphore(String var, IExp exp){
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        lock.lock();
        IDict<Integer, Pair<Integer, List<Integer>>> semaphore = state.getSemaphore().getSemaphore();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<IValue> heap = state.getHeapTable();
        IValue number1 = this.exp.eval(symTbl, heap);
        if(!number1.getType().equals(new IntType()))
            throw new Exception("expression is not int type");
        Integer number1_val = ((IntValue) number1).getVal();
        Integer loc = state.getSemaphore().getSemAddress();
        semaphore.add(loc, new Pair<>(number1_val, new ArrayList<>()));
        if(symTbl.isDefined(var))
        {
            if(symTbl.lookup(var).getType().equals(new IntType()))
            {
                IValue locVal = new IntValue(loc);
                symTbl.update(var, locVal);
            }
        }
        state.setSymTable(symTbl);
        state.setSemaphore(semaphore);
        lock.unlock();
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType var_type = typeEnv.lookup(var);
        IType exp_type = exp.typeCheck(typeEnv);
        if(!var_type.equals(new IntType()))
            throw new Exception("variable is not int");
        if(!exp_type.equals(new IntType()))
            throw new Exception("expression is not an int type");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new createSemaphore(var, exp);
    }

    @Override
    public String toString() {
        return "newSemaphore( " + var + ", " + exp.toString() + ")";
    }
}
