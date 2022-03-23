package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    String val;

    public StringValue() {
        this.val = "";
    }

    public StringValue(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(val);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringValue;
    }

    @Override
    public String toString() {
        return val;
    }
}
