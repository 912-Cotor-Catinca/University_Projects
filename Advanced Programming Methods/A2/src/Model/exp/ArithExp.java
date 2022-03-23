package Model.exp;

import Exceptions.DivisionByZero;
import Model.adt.IDict;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.Objects;

public class ArithExp extends Exp{
    private char op;
    private Exp e1, e2;
    public ArithExp(char op1, Exp _e1, Exp _e2){
        this.op = op1;
        this.e1 = _e1;
        this.e2 = _e2;
    }

    public char getOp(){return this.op;}
    public Exp getFirst(){return this.e1;}
    public Exp getSecond(){return this.e2;}

    @Override
    public IValue eval(IDict<String, IValue> symTable) throws Exception {
        IValue v1, v2;
        v1 = e1.eval(symTable);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(symTable);
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
}
