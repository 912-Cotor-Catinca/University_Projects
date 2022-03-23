package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.VarExp;
import Model.types.IType;
import Model.value.IValue;

public class VarDecl implements  IStmt{
    IType type;
    String name;
    public VarDecl(String name, IType type){
        this.name = name;
        this.type = type;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        if(symTbl.containsKey(this.name))
            throw new DeclaredExceptions("Variable is already declared.");
        else
        {
            IValue val = this.type.defaultValue();
            symTbl.add(this.name, val);
        }
        return state;
    }
    @Override
    public String toString(){
        return this.type+ " " +  this.name ;
    }
}
