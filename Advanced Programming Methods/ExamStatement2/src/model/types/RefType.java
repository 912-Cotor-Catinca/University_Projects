package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType {
    IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public IValue getDefault() {
        return new RefValue(1, inner);
    }

    @Override
    public IType deepCopy() {
        return new RefType(inner);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RefType)
            return inner.equals(((RefType) other).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return String.format("Ref(%s)", inner.toString());
    }
}
