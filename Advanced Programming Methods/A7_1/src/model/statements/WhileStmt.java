package model.statements;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStmt implements IStmt {
    IExp exp;
    IStmt stmt;

    public WhileStmt(IExp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stack = state.getExeStack();
        IValue expValue = exp.eval(state.getSymTable(), state.getHeapTable());
        if (expValue.getType().equals(new BoolType())) {
            if (((BoolValue)expValue).getVal()) {
                stack.push(this);
                stack.push(stmt);
            }
        } else {
            throw new TypeMismatchException("Condition of while statement not of type bool!");
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            stmt.typeCheck(typeEnv);
            return typeEnv;
        } else {
            throw new TypeMismatchException("Condition of while statement not of type bool!");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp, stmt);
    }

    @Override
    public String toString() {
        return String.format("while (%s){%s}", exp, stmt);
    }
}
