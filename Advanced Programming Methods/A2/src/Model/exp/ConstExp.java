package Model.exp;

import Model.adt.IDict;
import Model.value.IValue;

public class ConstExp extends Exp{
    private IValue number;

    public ConstExp(IValue _num){
        this.number = _num;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable) {
        return this.number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
