package Model.types;

import Model.value.IValue;
import Model.value.IntValue;

public class IntType implements Model.types.IType {
    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Model.types.IType deepCopy() {
        return new IntType();
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "int";
    }

}
