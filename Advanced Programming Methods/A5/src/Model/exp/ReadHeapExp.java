package Model.exp;

import Exceptions.DeclaredExceptions;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.IType;
import Model.type.RefType;
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
            else throw new Exception("The variable must be Model.type.RefType");
        }
        else throw new DeclaredExceptions("The variable must be Model.value.RefValue");
    }

    @Override
    public String toString() {
        return "rh(" + this.exp.toString() + ")";
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type = exp.typeCheck(typeEnv);
        if(type instanceof RefType){
            RefType ref = (RefType) type;
            return ref.getInner();
        }else throw new DeclaredExceptions("the rH argument is not a Ref Type");
    }
}
