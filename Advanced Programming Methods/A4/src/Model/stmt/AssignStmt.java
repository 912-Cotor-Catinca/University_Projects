package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;

public class AssignStmt implements IStmt{
    private String id;
    private Exp exp;
    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<Integer, IValue> heap = state.getHeapTable();
        if (symTbl.containsKey(this.id)){
            IValue val = this.exp.eval(symTbl, heap);
            IType typeId = (symTbl.lookup(id)).getType();
            if(val.getType().equals(typeId))
            {
                symTbl.update(this.id, val);
            }
            else
                throw new DeclaredExceptions("declared type of variable\"+id+\" and type of  the assigned expression do not match");
        }else
            throw new DeclaredExceptions("the used variable\" +id + \" was not declared before");
        return state;
    }

    @Override
    public String toString(){
        return this.id+"="+ this.exp.toString();
    }
}
