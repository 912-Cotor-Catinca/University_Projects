package Model.type;

import Model.value.IValue;
import Model.value.RefValue;

public class RefType implements IType{
    private IType inner;
    public RefType(IType inner){
        this.inner = inner;
    }

    public IType getInner(){return this.inner;}

    @Override
    public boolean equals(Object o){
        if(o instanceof RefType)
            return this.inner.equals(((RefType) o).getInner());
        else
            return false;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, this.inner);
    }

    @Override
    public String toString(){
        return "Ref(" +inner.toString()+")";
    }

    @Override
    public IType deepCopy() {
        return new RefType(this.inner);
    }
}
