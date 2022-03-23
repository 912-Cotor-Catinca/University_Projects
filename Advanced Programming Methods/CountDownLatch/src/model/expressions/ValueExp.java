package model.expressions;

import exceptions.MyException;
import model.adt.IDict;
import model.adt.IHeap;
import model.types.IType;
import model.values.IValue;

public class ValueExp implements IExp {
    IValue value;

    public ValueExp(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) {
        return value;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return value.getType();
    }

    @Override
    public IExp deepCopy() {
        return new ValueExp(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
