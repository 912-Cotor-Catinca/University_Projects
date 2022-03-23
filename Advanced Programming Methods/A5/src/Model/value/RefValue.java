package Model.value;

import Model.type.IType;
import Model.type.RefType;

public class RefValue implements IValue{
    private int address;
    private IType locationType;
    public RefValue(int i, IType inner) {
        this.address = i;
        this.locationType = inner;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof RefValue)
        {
            RefValue val = (RefValue) o;
            return val.getAddress() == this.address;
        }
        else
            return false;
    }

    public int getAddress(){return this.address;}

    public void setAddr(int newAddr){
        this.address = newAddr;
    }

    @Override
    public String toString(){
        return "("+ this.address + ", " + this.locationType.toString() +")";
    }

    @Override
    public IType getType() {
        return new RefType(this.locationType);
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(this.address, this.locationType);
    }

    public IType getLocType() {
        return this.locationType;
    }
}
