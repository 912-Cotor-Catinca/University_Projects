package model.statements;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.expressions.IExp;
import model.expressions.RelationalExp;
import model.expressions.VarExp;
import model.types.IType;
import model.types.IntType;

public class ForStmt implements IStmt{
    private IExp exp1, exp2, exp3;
    private IStmt stmt;
    private String var;

    public ForStmt(String var, IExp exp1, IExp exp2, IExp exp3, IStmt stmt){
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stack = state.getExeStack();
        IStmt newStmt = new CompStmt(new AssignStmt(var, exp1), new WhileStmt(new RelationalExp(new VarExp("v"), this.exp2, "<"),
                new CompStmt(stmt, new AssignStmt(var, this.exp3))));

        stack.push(newStmt);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType varType = typeEnv.lookup(var);
        IType typeExp1 = exp1.typeCheck(typeEnv);
        IType typeExp2 = exp2.typeCheck(typeEnv);
        IType typeExp3 = exp3.typeCheck(typeEnv);
        if(varType.equals(typeExp1))
        {
            if(varType.equals(typeExp2))
            {
                if(varType.equals(typeExp3))
                {
                    stmt.typeCheck(typeEnv);
                    return typeEnv;
                }throw new TypeMismatchException("Condition3 is not boolean");
            }throw new TypeMismatchException("Condition2 is not boolean");

        }
            throw new TypeMismatchException("Condition is not boolean");
    }

    @Override
    public String toString(){
        return "for (" + var + " = " + this.exp1.toString() + "; " + var + " < " + this.exp2.toString() + "; " + var + " = " + this.exp3.toString() + ") \n" + this.stmt.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(var, exp1, exp2, exp3, stmt);
    }
}
