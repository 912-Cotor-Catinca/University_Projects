package model.types;

import model.values.IValue;

public interface IType {
    boolean equals(Object other);
    IValue getDefault();
    IType deepCopy();
    String toString();
}
