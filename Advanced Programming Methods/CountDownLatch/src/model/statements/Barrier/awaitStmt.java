package model.statements.Barrier;

import model.PrgState;
import model.adt.IBarrier;
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

public class awaitStmt implements IStmt {
    String var;
    private static Lock lock = new ReentrantLock();
    public awaitStmt(String var)
    {
        this.var = var;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        IBarrier barrier = state.getBarrier();
        IDict<String , IValue> symbTbl = state.getSymTable();
        IStack<IStmt> stack = state.getExeStack();

        if(symbTbl.isDefined(var)) {
            IValue val =  symbTbl.lookup(var);
            Integer foundIndex = ((IntValue) val).getVal();
            if (barrier.getBarrier().isDefined(foundIndex)) {
                lock.lock();
                Pair<Integer, List<Integer>> barVal = barrier.getBarrier().lookup(foundIndex);
                List<Integer> threads = barVal.getSecond();
                Integer n1 = barVal.getFirst();
                Integer nl = threads.size();
                if (n1 > nl) {
                    if (threads.contains(state.getId()))
                        stack.push(this);
                    else {
                        threads.add(state.getId());
                        barrier.getBarrier().add(foundIndex, new Pair<>(n1, threads));
                        //barrier.setBarrier(barrier.getBarrier());
                    }
                }
                lock.unlock();
            }else throw new Exception("index is not in BarrierTable");
        } else throw new Exception("var is not in symbol table");
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
        return new awaitStmt(var);
    }

    @Override
    public String toString() {
        return "awaitBarrier( " + var + " )";
    }
}
