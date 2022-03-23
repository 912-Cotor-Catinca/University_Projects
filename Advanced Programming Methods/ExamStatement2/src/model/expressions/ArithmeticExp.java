package model.expressions;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import exceptions.UndefinedException;
import model.adt.IDict;
import model.adt.IHeap;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticExp implements IExp {
    IExp exp1, exp2;
    char op;

    public ArithmeticExp(IExp exp1, IExp exp2, char op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws MyException {
        IValue value1 = exp1.eval(symTable, heapTable), value2;
        if (value1.getType().equals(new IntType())) {
            value2 = exp2.eval(symTable, heapTable);
            if (value2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) value1, i2 = (IntValue) value2;
                int n1 = i1.getVal(), n2 = i2.getVal();
                switch (op) {
                    case '+':
                        return new IntValue(n1+n2);
                    case '-':
                        return new IntValue(n1-n2);
                    case '*':
                        return new IntValue(n1*n2);
                    case '/':
                        if (n2 == 0)
                            throw new ArithmeticException("Division by zero!");
                        else
                            return new IntValue(n1/n2);
                    default:
                        throw new UndefinedException(String.format("Undefined operator %c!", op));
                }
            } else {
                throw new TypeMismatchException(String.format("%s is not an integer!", exp2));
            }
        } else {
            throw new TypeMismatchException(String.format("%s is not an integer!", exp1));
        }
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new TypeMismatchException(String.format("%s is not an integer!", exp2));
            }
        } else {
            throw new TypeMismatchException(String.format("%s is not an integer!", exp1));
        }
    }

    @Override
    public IExp deepCopy() {
        return new ArithmeticExp(exp1, exp2, op);
    }

    @Override
    public String toString() {
        return String.format("%s %c %s", exp1.toString(), op, exp2.toString());
    }
}
