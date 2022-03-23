package model.types;

import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType {

    @Override
    public IValue getDefault() {
        return new IntValue();
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
}
