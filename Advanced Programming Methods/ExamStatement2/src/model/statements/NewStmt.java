package model.statements;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import exceptions.UndefinedException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class NewStmt implements IStmt {
    String name;
    IExp exp;

    public NewStmt(String name, IExp exp) {
        this.name = name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        IHeap<IValue> heapTable = state.getHeapTable();
        if (symTable.isDefined(name)) {
            if (symTable.lookup(name).getType() instanceof RefType) {
                IValue value = exp.eval(symTable, heapTable), tableValue = symTable.lookup(name);
                if (value.getType().equals(((RefType)(tableValue.getType())).getInner())) {
                    Integer address = heapTable.add(value);
                    symTable.update(name, new RefValue(address, value.getType()));
                } else {
                    throw new TypeMismatchException("Value is not of appropriate type!");
                }
            } else {
                throw new TypeMismatchException("Variable is not of reference type!");
            }
        } else {
            throw new UndefinedException("Variable has not been declared!");
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(name);
        IType typeExp = exp.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        } else {
            throw new TypeMismatchException("Right hand side and left hand side have different types in NEW statement");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(name, exp);
    }

    @Override
    public String toString() {
        return String.format("new(%s, %s)", name, exp);
    }
}
