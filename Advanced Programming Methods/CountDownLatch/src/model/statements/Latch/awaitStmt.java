package model.statements.Latch;

import model.PrgState;
import model.adt.IDict;
import model.adt.ILatchTable;
import model.adt.IStack;
import model.statements.IStmt;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class awaitStmt implements IStmt {
    String var;
    public awaitStmt(String var){
        this.var = var;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        IStack<IStmt> stack = state.getExeStack();
        ILatchTable latchTable = state.getLatchTable();
        if(!symTbl.isDefined(var))
            throw new Exception("var is not in Symbol table");
        IValue val = symTbl.lookup(var);
        Integer foundIndex = ((IntValue)val).getVal();
        if(!latchTable.isDefined(foundIndex))
            throw new Exception("index is not in latch table");
        else if(latchTable.lookup(foundIndex) != 0) {
            stack.push(this);
        }

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
    public String toString(){
        return "await(" + var + ")";
    }
}
