package model.expressions;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import exceptions.UndefinedException;
import model.adt.IDict;
import model.adt.IHeap;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;


public class ReadHeapExp implements IExp {
    IExp exp;

    public ReadHeapExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws MyException {
        IValue value = exp.eval(symTable, heapTable);
        if (value instanceof RefValue refValue) {
            Integer address = refValue.getAddress();
            if (heapTable.exists(address)) {
                return heapTable.get(address);
            }
            else {
                throw new UndefinedException("Value not allocated in the heap!");
            }
        }
        else {
            throw new TypeMismatchException("Expression not of reference type!");
        }
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type = exp.typeCheck(typeEnv);
        if (type instanceof RefType refType) {
            return refType.getInner();
        } else {
            throw new TypeMismatchException(String.format("%s is not of ref type!", exp));
        }
    }

    @Override
    public IExp deepCopy() {
        return new ReadHeapExp(exp);
    }

    @Override
    public String toString() {
        return String.format("readHeap(%s)", exp);
    }
}
