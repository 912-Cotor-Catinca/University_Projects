package model.statements;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IHeap;
import model.adt.IStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStmt implements IStmt {
    IExp exp;
    IStmt thenS, elseS;

    public IfStmt(IExp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stack = state.getExeStack();
        IDict<String, IValue> symTable = state.getSymTable();
        IHeap<IValue> heapTable = state.getHeapTable();
        IValue value = exp.eval(symTable, heapTable);
        if (value.getType().equals(new BoolType())) {
            BoolValue condition = (BoolValue) value;
            if (condition.getVal()) {
                stack.push(thenS);
            } else {
                stack.push(elseS);
            }
            return null;
        } else {
            throw new TypeMismatchException("Condition of if statement not of type bool!");
        }
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenS.typeCheck(typeEnv);
            elseS.typeCheck(typeEnv);
            return typeEnv;
        } else {
            throw new TypeMismatchException("Condition of if statement not of type bool!");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp, thenS, elseS);
    }

    @Override
    public String toString() {
        return String.format("if (%s){%s} else{%s}", exp.toString(), thenS.toString(), elseS.toString());
    }
}
