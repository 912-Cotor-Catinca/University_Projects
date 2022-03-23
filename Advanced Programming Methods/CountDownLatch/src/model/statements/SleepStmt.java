package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.types.IType;

public class SleepStmt implements IStmt{
    Integer number;
    public SleepStmt(Integer nr){
        this.number = nr;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        if(this.number != 0) {
            IStack<IStmt> stack = state.getExeStack();
            stack.push(new SleepStmt(number-1));
            state.setExeStack(stack);
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(number);
    }

    @Override
    public String toString(){
        return "sleep( " + number.toString() + ");";
    }
}
