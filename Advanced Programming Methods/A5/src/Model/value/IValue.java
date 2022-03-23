package Model.value;

import Model.type.IType;

public interface IValue {
    IType getType();
    IValue deepCopy();
    boolean equals(Object o);
    String toString();
}
