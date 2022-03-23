package Model.exp;

import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.BoolType;
import Model.type.IType;
import Model.type.IntType;
import Model.type.StringType;
import Model.value.IValue;

public class ValueExp extends Model.exp.Exp {
    private IValue val;
    public ValueExp(IValue val){
        this.val = val;
    }
    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws Exception {
        if(this.val.getType().equals(new IntType()) || this.val.getType().equals(new BoolType())||
            this.val.getType().equals(new StringType()))
            return this.val;
        else
            throw new Exception("Unknown type\n");
    }

    @Override
    public String toString() {
        return this.val.toString();
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        return val.getType();
    }
}
