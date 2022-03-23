package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.type.IType;
import Model.value.IValue;

public class VarDecl implements Model.stmt.IStmt {
    IType type;
    String name;
    public VarDecl(String name, IType type){
        this.name = name;
        this.type = type;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<Model.stmt.IStmt> stack = state.getStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        if(symTbl.containsKey(this.name))
            throw new DeclaredExceptions("Variable is already declared.");
        else
        {
            IValue val = this.type.defaultValue();
            symTbl.add(this.name, val);
        }
        return null;
    }
    @Override
    public String toString(){
        return this.type+ " " +  this.name ;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        typeEnv.add(name, type);
        return typeEnv;
    }
}
