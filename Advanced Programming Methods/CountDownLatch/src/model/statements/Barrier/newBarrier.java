package model.statements.Barrier;

import model.PrgState;
import model.adt.IBarrier;
import model.adt.IDict;
import model.adt.Pair;
import model.expressions.IExp;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newBarrier implements IStmt {
    String var;
    IExp exp;
    private static Lock lock = new ReentrantLock();
    public newBarrier(String var, IExp exp){
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        lock.lock();
        try{
            IDict<String, IValue> symTbl = state.getSymTable();
            IValue expVal = this.exp.eval(symTbl, state.getHeapTable());

            IBarrier barrier = state.getBarrier();
            if(symTbl.isDefined(var)){
                if(expVal.getType().equals(new IntType())){
                    Integer number = ((IntValue)expVal).getVal();
                    int barrierIndex = barrier.getBarrierAddress();
                    barrier.put(barrierIndex, new Pair<>(number, new ArrayList<>()));
                    symTbl.update(var, new IntValue(barrierIndex));
                }
            }else throw new Exception("var not in symbol table");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
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
        return new newBarrier(var, exp);
    }
    @Override
    public String toString() {
        return "NewBarrierStmt(" + var + ", "+ exp.toString() + ")";
    }
}
