package model.statements.Semaphore;

import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.adt.Pair;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Acquire implements IStmt {
    String var;
    private static Lock lock = new ReentrantLock();

    public Acquire(String var){
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        lock.lock();
        try{
            IDict<String, IValue> symTbl = state.getSymTable();
            IStack<IStmt> stack = state.getExeStack();
            IDict<Integer, Pair<Integer, List<Integer>>> semaphore = state.getSemaphore().getSemaphore();

            IValue val_index = symTbl.lookup(var);
            Integer foundIndex = ((IntValue)val_index).getVal();

            if (!symTbl.isDefined(var))
                throw new Exception("var is not in Symbol table");
            if(!semaphore.isDefined(foundIndex))
                throw new Exception("foundindex not in semaphore table");
            else
            {
                Pair<Integer, List<Integer>> semVal = semaphore.lookup(foundIndex);
                List<Integer> threads = semVal.getValue();
                Integer MaxNr = semVal.getFirst();
                if(MaxNr > threads.size()){
                    if(threads.contains(state.getId())){
                        throw new Exception("Already in process");
                    }
                    threads.add(state.getId());
                    state.getSemaphore().put(foundIndex, new Pair<>(MaxNr, threads));
                }else
                {
                    stack.push(this);
                    state.setExeStack(stack);
                }
            }
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
        IType type_var = typeEnv.lookup(var);
        if(!type_var.equals(new IntType()))
            throw new Exception("Variable does not have int type");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new Acquire(var);
    }
    @Override
    public String toString() {
        return "acquire( " + var + " )";
    }
}
