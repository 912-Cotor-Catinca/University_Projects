package Model.exp;

import Model.adt.IDict;
import Model.value.IValue;

public class VarExp extends Exp{
    private String id;

    public VarExp(String id){
        this.id = id;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable) {
        return symTable.lookup(id);
    }

    @Override
    public String toString() {
        return this.id;
    }
}
