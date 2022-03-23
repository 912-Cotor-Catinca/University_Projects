package model.statements.Lock;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.adt.ILockTable;
import model.adt.IStack;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStatement implements IStmt {
    String var;
    private static Lock lock = new ReentrantLock();

    public LockStatement(String var){
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {

        IDict<String, IValue> symTbl = state.getSymTable();
        if(!symTbl.isDefined(var))
            throw new Exception("the key does not exist");
        ILockTable<Integer> lockTable = state.getLockTable();
        IStack<IStmt> stack = state.getExeStack();
        IntValue val = (IntValue) symTbl.lookup(var);
        Integer location = val.getVal();
        Integer lockVal = lockTable.lookup(location);
        if(lockTable.isDefined(val.getVal())) {
            lock.lock();
            if (lockVal == null)
                throw new Exception("The location does not exist");
            if (lockVal == -1) {
                lockTable.update(location, lockVal);
                state.setLockTable(lockTable);
            } else {
                stack.push(this);
                state.setExeStack(stack);
            }

            lock.unlock();
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type_var = typeEnv.lookup(var);
        if(!type_var.equals(new IntType()))
            throw new Exception("variable is not int");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "lock(" + var + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new LockStatement(this.var);
    }
}
