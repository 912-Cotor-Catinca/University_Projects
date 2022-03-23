package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapAllocStmt implements IStmt{
    private final String varName;
    private final Exp exp;

    public HeapAllocStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<Integer, IValue> heapTbl = state.getHeapTable();

        if(symTbl.containsKey(this.varName)){
            IValue val = symTbl.lookup(varName);
            if(val.getType() instanceof RefType){
                IValue evalExp = this.exp.eval(symTbl, heapTbl);
                RefType ref = (RefType) (val.getType());
                if(evalExp.getType().equals(ref.getInner())){
                    heapTbl.add(evalExp);
                    RefValue new_val = new RefValue((Integer)heapTbl.getAddress(evalExp), evalExp.getType());
                    symTbl.update(this.varName, new_val);
                }else throw new Exception("The variable type and expression type does not match");
            }else throw new DeclaredExceptions("The variable must be RefType");
        }else throw new DeclaredExceptions("Undefined variable");
        return state;
    }

    @Override
    public String toString(){
        return "wH(" + this.varName + " = " + this.exp.toString() + ")";
    }
}
