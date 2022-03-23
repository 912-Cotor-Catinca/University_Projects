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

public class WriteHeapStmt implements IStmt {
    String name;
    IExp exp;

    public WriteHeapStmt(String name, IExp exp) {
        this.name = name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        IHeap<IValue> heapTable = state.getHeapTable();
        if (symTable.isDefined(name)) {
            IValue value = symTable.lookup(name);
            if (value instanceof RefValue) {
                Integer address = ((RefValue) value).getAddress();
                if (heapTable.exists(address)) {
                    IValue expValue = exp.eval(symTable, heapTable);
                    if (expValue.getType().equals(heapTable.get(address).getType())) {
                        heapTable.update(address, expValue);
                    } else {
                        throw new TypeMismatchException("Expression value not of correct type!");
                    }
                } else {
                    throw new UndefinedException("Value does not exist in the heap!");
                }
            } else {
                throw new TypeMismatchException("Value is not of reference type!");
            }
        } else {
            throw new UndefinedException("Variable has not been defined!");
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
            throw new TypeMismatchException("Right hand side and left hand side have different types in write statement");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(name, exp);
    }

    @Override
    public String toString() {
        return String.format("writeHeap(%s, %s)", name, exp);
    }
}
