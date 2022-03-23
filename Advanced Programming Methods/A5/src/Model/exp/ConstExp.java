package Model.exp;

import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.IType;
import Model.type.IntType;
import Model.value.IValue;

public class ConstExp extends Model.exp.Exp {
    private IValue number;

    public ConstExp(IValue _num){
        this.number = _num;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heapTbl) {
        return this.number;
    }

    @Override
    public String toString() {
        return number.toString();
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        return new IntType();
    }
}
