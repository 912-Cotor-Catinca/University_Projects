package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType {

    @Override
    public IValue getDefault() {
        return new StringValue();
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }
}
