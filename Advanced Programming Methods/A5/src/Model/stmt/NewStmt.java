package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.exp.Exp;
import Model.type.IType;
import Model.type.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class NewStmt implements IStmt{
    private final String var_name;
    private final Exp exp;

    public NewStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<Integer,IValue> heapTbl = state.getHeapTable();

        if(symTbl.containsKey(var_name)){
            IValue val = symTbl.lookup(var_name);
            if (val.getType() instanceof RefType){
                IValue cond = exp.eval(symTbl, heapTbl);
                RefValue refVal = (RefValue) val;
                if(cond.getType().equals(refVal.getLocType())){
                    int pos = heapTbl.add(cond);
                    RefValue copyref = (RefValue) refVal.deepCopy();
                    copyref.setAddr(pos);
                    symTbl.update(var_name, copyref);
                }else throw new Exception("The variable type and expression type does not match");
            }else throw new DeclaredExceptions("The variable must be Model.type.RefType");
        }else throw new DeclaredExceptions("Undefined variable");
        return null;

    }

    @Override
    public String toString(){
        return "(new " + var_name + " " + exp.toString() + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type_var = typeEnv.lookup(var_name);
        IType type_exp = exp.typeCheck(typeEnv);
        if(type_var.equals(new RefType(type_exp))){
            return typeEnv;
        }else throw new DeclaredExceptions("NEW stmt: right hand side and left hand side have different types");
    }
}
