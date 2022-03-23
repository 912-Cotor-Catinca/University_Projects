package model.types;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType {

    @Override
    public IValue getDefault() {
        return new BoolValue();
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
}
