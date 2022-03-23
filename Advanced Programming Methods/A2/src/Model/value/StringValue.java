package Model.value;

import Model.types.IType;
import Model.types.StringType;

public class StringValue implements IValue{
    private String val;
    public StringValue(){
        this.val="";
    }
    public StringValue(String v){
        this.val = v;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof StringValue){
            StringValue o_val = (StringValue) o;
            return o_val.getvalue().equals(this.val);
        }
        return false;
    }

    public String getvalue() {
        return this.val;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.val);
    }
    @Override
    public String toString(){
        return "'" + this.val + "'";
    }


}
