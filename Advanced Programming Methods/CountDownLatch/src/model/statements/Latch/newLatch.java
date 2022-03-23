package model.statements.Latch;

import model.PrgState;
import model.adt.IDict;
import model.adt.IHeap;
import model.adt.ILatchTable;
import model.adt.IStack;
import model.expressions.IExp;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newLatch implements IStmt {
    String var;
    IExp exp;

    private static Lock lock = new ReentrantLock();

    public newLatch(String var, IExp exp){
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        lock.lock();
        try{
            IDict<String, IValue> symTbl = state.getSymTable();
            IStack<IStmt> stack = state.getExeStack();
            IHeap<IValue> heap = state.getHeapTable();
            IValue val = exp.eval(symTbl, heap);
            if(!val.getType().equals(new IntType()))
                throw new Exception("expression is not int");
            Integer num1 = ((IntValue)val).getVal();
            ILatchTable latchTable = state.getLatchTable();
            Integer freeLocation = latchTable.getfreeLoc();
            latchTable.add(freeLocation, num1);
            IValue locVal = new IntValue(freeLocation);
            symTbl.add(var, locVal);

        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

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
        return new newLatch(var, exp);
    }

    @Override
    public String toString() {
        return "newLatch(" + this.var + ", " + this.exp.toString() + ")";
    }
}
