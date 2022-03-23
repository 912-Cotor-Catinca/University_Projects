package model.expressions;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import model.adt.IDict;
import model.adt.IHeap;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class MUL implements IExp {
    IExp exp1, exp2;
    public MUL(IExp exp1, IExp exp2){
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws MyException {
        IValue value1 = exp1.eval(symTable, heapTable), value2;
        if(value1.getType().equals(new IntType())){
            value2 = exp2.eval(symTable, heapTable);
            if(value2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) value1, i2 = (IntValue) value2;
                int n1 = i1.getVal(), n2 = i2.getVal();
                return new IntValue((n1*n2) -(n1+n2));
            }throw new TypeMismatchException(String.format("%s is not an integer!", exp2));
        }throw new TypeMismatchException(String.format("%s is not an integer!", exp1));
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type_exp1 = exp1.typeCheck(typeEnv);
        IType type_exp2 = exp2.typeCheck(typeEnv);
        if(!type_exp1.equals(new IntType()) && !type_exp2.equals(new IntType()))
            throw new Exception("expressions are not int");
        return new IntType();
    }

    @Override
    public IExp deepCopy() {
        return new MUL(exp1, exp2);
    }
}
