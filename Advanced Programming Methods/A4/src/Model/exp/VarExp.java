package Model.exp;

import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.value.IValue;

public class VarExp extends Exp {
    private String id;

    public VarExp(String id){
        this.id = id;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) {
        return symTable.lookup(id);
    }

    @Override
    public String toString() {
        return this.id;
    }
}
