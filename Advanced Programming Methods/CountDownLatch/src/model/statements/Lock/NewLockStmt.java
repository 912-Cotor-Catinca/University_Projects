package model.statements.Lock;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.adt.ILockTable;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt {
    String var;
    private static Lock lock = new ReentrantLock();
    public NewLockStmt(String var){
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        IDict<String, IValue> symTbl = state.getSymTable();
        ILockTable<Integer> lockTable = state.getLockTable();
        Integer location = lockTable.getFreeLocation();
        IValue val = new IntValue(location);
        lockTable.add(location,-1);
        symTbl.add(var, val);

        state.setSymTable(symTbl);
        state.setLockTable(lockTable);
        lock.unlock();
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
    public IStmt deepCopy() {
        return new NewLockStmt(var);
    }

    @Override
    public String toString(){
        return "newLock(" + var + ")";
    }
}
