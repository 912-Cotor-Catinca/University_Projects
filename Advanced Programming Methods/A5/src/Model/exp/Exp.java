package Model.exp;

import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.IType;
import Model.value.IValue;

public abstract class Exp {
    public abstract IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heapTbl) throws Exception;
    public abstract String toString();
    public abstract IType typeCheck(IDict<String, IType> typeEnv) throws Exception;
}
