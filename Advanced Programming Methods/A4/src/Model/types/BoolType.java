package Model.types;

import Model.value.BoolValue;
import Model.value.IValue;

public class BoolType implements Model.types.IType {
    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null || o.getClass() != this.getClass())
            return false;
        return true;
    }
    @Override
    public String toString(){
        return "bool";
    }

    @Override
    public Model.types.IType deepCopy() {
        return new BoolType();
    }
}
