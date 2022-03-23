package model.statements;

import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.types.IType;

public class CondStmt implements IStmt{
    String var;
    IExp exp1, exp2, exp3;

    public CondStmt(String var, IExp exp1, IExp exp2, IExp exp3){
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getExeStack();
        IStmt newStmt = new IfStmt(exp1, new AssignStmt(var, exp2), new AssignStmt(var, exp3));
        stack.push(newStmt);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {

        IType type1 = exp1.typeCheck(typeEnv);

        if(type1.equals(new BoolType())){
            IType typeVar = typeEnv.lookup(var);
            IType type2 = exp2.typeCheck(typeEnv);
            IType type3 = exp3.typeCheck(typeEnv);
            if(typeVar.equals(type3) && typeVar.equals(type2))
                return typeEnv;
            else
                throw new Exception("var, exp2, exp3 do not have the same type");
        }
        else
            throw new Exception("exp1 is not boolean");

    }

    @Override
    public IStmt deepCopy() {
        return new CondStmt(var, exp1,exp2,exp3);
    }

    @Override
    public String toString(){
        return var + "=" + exp1.toString() + "?" + exp2.toString() + ":" + exp3.toString();
    }
}
