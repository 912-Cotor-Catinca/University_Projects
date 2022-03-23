package Model.exp;

import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.value.IValue;

public abstract class Exp {
    public abstract IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heapTbl) throws Exception;
    public abstract String toString();

}
