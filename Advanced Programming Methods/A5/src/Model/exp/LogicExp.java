package Model.exp;

import Exceptions.DeclaredExceptions;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.BoolType;
import Model.type.IType;
import Model.value.BoolValue;
import Model.value.IValue;

import java.util.Objects;

public class LogicExp extends Exp{
    private String op;
    private Exp e1, e2;
    public LogicExp(String op, Exp e1, Exp e2){
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }
    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heapTbl) throws Exception {
        IValue v1, v2;
        v1 = e1.eval(symTable, heapTbl);
        if(v1.getType().equals(new BoolType())){
            v2 = e2.eval(symTable, heapTbl);
            if(v2.getType().equals(new BoolType())){
                BoolValue b1, b2;
                b1 = (BoolValue) v1;
                b2 = (BoolValue) v2;
                boolean n1 = b1.getVal();
                boolean n2 = b2.getVal();
                if(Objects.equals(op, "and"))
                    return new BoolValue(n1 && n2);
                else if (Objects.equals(op, "or")){
                    return new BoolValue(n1 || n2);
                }
            }
            else throw new Exception("the second is not boolean");
        }
        else throw new Exception("the first is not boolean");
        return null;
    }

    @Override
    public String toString() {
        return this.e1.toString() + op + this.e2.toString();
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);

        if(type1.equals(new BoolType())){
            if(type2.equals(new BoolType())){
                return new BoolType();
            }else throw new DeclaredExceptions("Second operand is not a bool type");
        }else throw new DeclaredExceptions("First operand is not a bool type");
    }
}
