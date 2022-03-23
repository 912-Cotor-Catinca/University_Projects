package model.expressions;

import exceptions.MyException;
import model.adt.IDict;
import model.adt.IHeap;
import model.types.IType;
import model.values.IValue;

public class VarExp implements IExp {
    String key;

    public VarExp(String key) {
        this.key = key;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) {
        return symTable.lookup(key);
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv.lookup(key);
    }

    @Override
    public IExp deepCopy() {
        return new VarExp(key);
    }

    @Override
    public String toString() {
        return key;
    }
}
