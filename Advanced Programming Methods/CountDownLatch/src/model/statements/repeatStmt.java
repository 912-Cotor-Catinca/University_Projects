package model.statements;

import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.expressions.IExp;
import model.types.IType;

import java.util.stream.IntStream;

public class repeatStmt implements IStmt{
    IStmt stmt;
    IExp exp;
    public repeatStmt(IStmt stmt, IExp exp){
        this.stmt = stmt;
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getExeStack();
        IStmt newStmt = new CompStmt(stmt, new WhileStmt(exp,stmt,false));
        stack.push(newStmt);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new repeatStmt(stmt, exp);
    }

    @Override
    public String toString(){
        return "repeat{ \n " + stmt.toString() + "\n}until " + exp.toString();
    }
}
