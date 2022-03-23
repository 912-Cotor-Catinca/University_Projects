package model.expressions;

import exceptions.MyException;
import model.adt.IDict;
import model.adt.IHeap;
import model.types.IType;
import model.values.IValue;

public interface IExp {
    IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws MyException;
    IType typeCheck(IDict<String, IType> typeEnv) throws MyException;
    IExp deepCopy();
}
