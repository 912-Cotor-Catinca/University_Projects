package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IList;
import model.expressions.IExp;
import model.types.IType;

public class PrintStmt implements IStmt {
    IExp exp;

    public PrintStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IList<String> output = state.getOut();
        output.add(exp.eval(state.getSymTable(), state.getHeapTable()).toString());
        state.setOut(output);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp);
    }


    @Override
    public String toString() {
        return String.format("print(%s)", exp.toString());
    }
}
