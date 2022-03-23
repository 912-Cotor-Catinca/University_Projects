package model.expressions;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import exceptions.UndefinedException;
import model.adt.IDict;
import model.adt.IHeap;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicExp implements IExp {
    IExp exp1, exp2;
    String op;

    public LogicExp(IExp exp1, IExp exp2, String op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws MyException {
        IValue value1 = exp1.eval(symTable, heapTable), value2;
        if (value1.getType().equals(new BoolType())) {
            value2 = exp2.eval(symTable, heapTable);
            if (value2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) value1, b2 = (BoolValue) value2;
                boolean n1 = b1.getVal(), n2 = b2.getVal();
                return switch (op) {
                    case "&&" -> new BoolValue(n1 && n2);
                    case "||" -> new BoolValue(n1 || n2);
                    default -> throw new UndefinedException(String.format("Undefined operator %s!", op));
                };
            }
            else {
                throw new TypeMismatchException(String.format("%s is not a boolean!", exp2));
            }
        }
        else {
            throw new TypeMismatchException(String.format("%s is not a boolean!", exp1));
        }
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new TypeMismatchException(String.format("%s is not a boolean!", exp2));
            }
        } else {
            throw new TypeMismatchException(String.format("%s is not a boolean!", exp1));
        }
    }

    @Override
    public IExp deepCopy() {
        return new LogicExp(exp1, exp2, op);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", exp1.toString(), op, exp2.toString());
    }
}
