package Model.exp;

import Exceptions.DeclaredExceptions;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class ReadHeapExp extends Exp{
    private Exp exp;
    public ReadHeapExp(Exp exp){
        this.exp = exp;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heapTbl) throws Exception {
        IValue val = this.exp.eval(symTable, heapTbl);
        if(val.getType() instanceof RefType){
            int address = ((RefValue) val).getAddress();
            if(heapTbl.containsKey(address)){
                return heapTbl.lookup(address);
            }
            else throw new Exception("The variable must be RefType");
        }
        else throw new DeclaredExceptions("The variable must be RefValue");
    }

    @Override
    public String toString() {
        return "rh(" + this.exp.toString() + ")";
    }
}
