package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue {
    boolean val;

    public BoolValue() {
        this.val = false;
    }

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BoolValue;
    }

    @Override
    public String toString() {
        return val ? "true" : "false";
    }
}
