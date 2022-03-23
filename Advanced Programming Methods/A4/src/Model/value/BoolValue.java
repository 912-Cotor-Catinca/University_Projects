package Model.value;

import Model.types.BoolType;
import Model.types.IType;

public class BoolValue implements IValue{
    boolean value;
    public BoolValue(){
        this.value = false;
    }
    public BoolValue(boolean _val){
        this.value = _val;
    }

    public boolean getVal(){return this.value;}

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(this.value);
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        BoolValue o_val = (BoolValue) o;
        return o_val.value == this.value;
    }
    @Override
    public String toString(){
        return this.value ? "true" : "false";
    }
}
