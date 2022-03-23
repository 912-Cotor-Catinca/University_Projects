package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.expressions.ValueExp;
import model.types.IType;
import model.values.IntValue;

public class WaitStmt implements IStmt{
    Integer number;
    public WaitStmt(Integer nr){
        this.number = nr;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getExeStack();
        if(number != 0){
            stack.push(new CompStmt(new PrintStmt(new ValueExp(new IntValue(number))), new WaitStmt(number-1)));
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
        return new WaitStmt(number);
    }

    @Override
    public String toString(){
        return "wait(" + number.toString() + ")";
    }
}
