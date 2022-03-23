package Model.types;

import Model.value.IValue;
import Model.value.StringValue;

public class StringType implements Model.types.IType {
    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass()){
            return false;
        }
        return true;
    }

    @Override
    public Model.types.IType deepCopy() {
        return new StringType();
    }

    @Override
    public String toString(){
        return "string";
    }
}
