package Model.exp;

import Exceptions.DeclaredExceptions;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

public class RelationalExp extends Exp{
    private Exp e1, e2;
    private String op;
    public RelationalExp(Exp e1, Exp e2, String op){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heapTbl) throws Exception {
        IValue v1 = e1.eval(symTable, heapTbl);
        if(v1.getType().equals(new IntType())){
            IValue v2 = e2.eval(symTable, heapTbl);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                switch (op){
                    case ">":
                        return new BoolValue(i1.getVal() > i2.getVal());
                    case "<":
                        return new BoolValue(i1.getVal() < i2.getVal());
                    case ">=":
                        return new BoolValue(i1.getVal() >= i2.getVal());
                    case "<=":
                        return new BoolValue(i1.getVal() <= i2.getVal());
                    case "==":
                        return new BoolValue(i1.getVal() == i2.getVal());
                    case "!=":
                        return new BoolValue(i1.getVal() != i2.getVal());
                }
            }
            else{
                throw new DeclaredExceptions("Operand 2 is not an integer");
            }
        }else{
            throw new DeclaredExceptions("Operand 1 is not an integer");
        }
        return new BoolValue();
    }

    @Override
    public String toString() {
        return this.e1.toString() + " "+ this.op + " "+ this.e2.toString();
    }
}
