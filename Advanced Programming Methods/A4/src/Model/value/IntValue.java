package Model.value;

import Model.types.IType;
import Model.types.IntType;

public class IntValue implements IValue{
    int value;
    public IntValue(){
        this.value = 0;
    }

    public IntValue(int val){
        this.value = val;
    }

    public int getVal(){
        return this.value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(this.value);
    }

    @Override
    public boolean equals(Object o){
        if (o == null || o.getClass() != this.getClass())
            return false;
        IntValue new_val = (IntValue) o;
        return new_val.value == this.value;
    }

    @Override
    public String toString(){
        return Integer.toString(this.value);
    }

}
