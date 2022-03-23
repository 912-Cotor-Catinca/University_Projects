package model.statements.ToySem;

import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.adt.IToySem;
import model.adt.Triplet;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class release implements IStmt {
    String var;
    private static Lock lock = new ReentrantLock();
    public release(String var){
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        IStack<IStmt> stack = state.getExeStack();
        IToySem sem = state.getToySem();
        lock.lock();
        if(symTbl.isDefined(var)){
            int foundIndex = ((IntValue)symTbl.lookup(var)).getVal();
            if(sem.getSemaphore().isDefined(foundIndex)){
                Triplet<Integer, ArrayList<Integer>, Integer> entry = sem.getSemaphore().lookup(foundIndex);
                ArrayList<Integer> list = entry.getSecond();
                int N1 = entry.getFirst();
                int N2 = entry.getThird();
                if(list.contains(state.getId())) {
                    list.remove(state.getId());
                }
                sem.getSemaphore().update(foundIndex, new Triplet<>(N1, list, N2));
                lock.unlock();
            }else throw new Exception("index not in semaphore");
        }else throw new Exception("var not in symbol table");
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        if(!typeEnv.isDefined(var))
            throw new Exception("Variable is not defined");
        IType type_exp = typeEnv.lookup(var);
        if(!type_exp.equals(new IntType()))
            throw new Exception("Variable is not a int type");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new release(var);
    }

    @Override
    public String toString() {
        return "releaseToy(" + var + ")";
    }
}
