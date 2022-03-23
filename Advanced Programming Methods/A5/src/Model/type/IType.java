package Model.type;

import Model.value.IValue;

public interface IType {
    IValue defaultValue();
    IType deepCopy();
    String toString();
}
