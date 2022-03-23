package Model.exp;

import Exceptions.DivisionByZero;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.IType;
import Model.type.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.Objects;

public class ArithExp extends Model.exp.Exp {
    private char op;
    private Model.exp.Exp e1, e2;
    public ArithExp(char op1, Model.exp.Exp _e1, Model.exp.Exp _e2){
        this.op = op1;
        this.e1 = _e1;
        this.e2 = _e2;
    }

    public char getOp(){return this.op;}
    public Model.exp.Exp getFirst(){return this.e1;}
    public Model.exp.Exp getSecond(){return this.e2;}

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heapTbl) throws Exception {
        IValue v1, v2;
        v1 = e1.eval(symTable, heapTbl);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(symTable, heapTbl);
            if (Objects.equals(v2.getType(), new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == '+')
                    return new IntValue(n1 + n2);
                else if (op == '-')
                    return new IntValue(n1 - n2);
                else if (op == '*')
                    return new IntValue(n1 * n2);
                else if (op == '/')
                    if (n2 == 0) throw new DivisionByZero("Division by 0");
                    else return new IntValue(n1 / n2);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if(type1.equals(new IntType())){
            if (type2.equals(new IntType())){
                return new IntType();
            }
            else throw new Exception("second operand is not an integer");
        }else throw new Exception("first operand is not an integer");
    }
}
