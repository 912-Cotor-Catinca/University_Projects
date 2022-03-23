package model.statements.Latch;

import model.PrgState;
import model.adt.IDict;
import model.adt.ILatchTable;
import model.adt.IStack;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.statements.IStmt;
import model.statements.PrintStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class countDown implements IStmt {
    String var;
    private static Lock lock = new ReentrantLock();

    public countDown(String var){
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        lock.lock();
        IDict<String, IValue> symTbl = state.getSymTable();
        IStack<IStmt> stack = state.getExeStack();
        ILatchTable latchTable = state.getLatchTable();
        if(!symTbl.isDefined(var))
            throw new Exception("var is not in Symbol table");
        IValue val = symTbl.lookup(var);
        Integer foundIndex = ((IntValue)val).getVal();


        if(!latchTable.isDefined(foundIndex))
            throw new Exception("index does not exist in latch table");
        else if(latchTable.lookup(foundIndex) > 0){
            latchTable.add(foundIndex, latchTable.lookup(foundIndex)-1);
            state.getOut().add(state.getId().toString());
        }
        else
        {
            state.getOut().add(state.getId().toString());
        }

        lock.unlock();
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
        return new countDown(var);
    }

    @Override
    public String toString(){
        return "countDown( " + var + " )";
    }
}
