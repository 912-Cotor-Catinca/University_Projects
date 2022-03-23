package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IHeap;
import model.adt.IStack;
import model.expressions.IExp;
import model.types.IType;
import model.values.IValue;

public class Switch implements IStmt{
    IExp exp, exp1, exp2;
    IStmt stmt1, stmt2, stmt3;
    public Switch(IExp exp, IExp exp1, IStmt stmt1, IExp exp2, IStmt stmt2, IStmt stmt3){
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getExeStack();

        IDict<String, IValue> symTable = state.getSymTable();
        IHeap<IValue> heapTable = state.getHeapTable();
        IValue value = exp.eval(symTable, heapTable);
        IValue value1 = exp1.eval(symTable, heapTable);
        IValue value2 = exp2.eval(symTable, heapTable);

        IStmt newStmt;

        if(value.equals(value1)){
            newStmt = stmt1;
        }
        else if(value.equals(value2)){
            newStmt = stmt2;
        }
        else
            newStmt = stmt3;

        stack.push(newStmt);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType typeExp = exp.typeCheck(typeEnv);
        IType typeExp1 = exp1.typeCheck(typeEnv);
        IType typeExp2 = exp2.typeCheck(typeEnv);
        if(typeExp.equals(typeExp1) && typeExp.equals(typeExp2)){
            stmt1.typeCheck(typeEnv);
            stmt2.typeCheck(typeEnv);
            stmt3.typeCheck(typeEnv);
            return typeEnv;
        }else throw new Exception("the expressions do not have the same type");

    }

    @Override
    public IStmt deepCopy() {
        return new Switch(exp, exp1, stmt1,exp2 , stmt2, stmt3);
    }

    @Override
    public String toString(){
        return "switch(" + exp.toString() + "\n(case(" + exp1.toString() + ")" + stmt1.toString() + "\n (case("+
                exp2.toString() + ")" +stmt2.toString() + ")" + "\n (default: " + stmt3.toString() + ")";
    }
}
