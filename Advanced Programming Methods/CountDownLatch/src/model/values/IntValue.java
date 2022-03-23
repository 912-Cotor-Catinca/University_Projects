package model.values;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue {
    int val;

    public IntValue() {
        this.val = 0;
    }

    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(val);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntValue;
    }

    @Override
    public String toString() {
        return String.format("%d", val);
    }
}
