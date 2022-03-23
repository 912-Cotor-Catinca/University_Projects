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

public class WriteHeapStmt implements Model.stmt.IStmt {
    private final String var_name;
    private final Exp expression;

    public WriteHeapStmt(String var_name, Exp expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<Integer, IValue> heapTbl = state.getHeapTable();

        if(symTbl.containsKey(var_name)){
            IValue val = symTbl.lookup(var_name);
            if(val.getType() instanceof RefType){
                int address = ((RefValue)val).getAddress();
                if(heapTbl.containsKey(address)){
                    IValue expval = expression.eval(symTbl, heapTbl);
                    IType locType = ((RefValue)val).getLocType();
                    if(expval.getType().equals(locType)){
                        heapTbl.update(address, expval);
                    }else throw new Exception("Invalid type");
                }else throw new Exception("Uninitialized address memory");
            }else throw new DeclaredExceptions("The variable must be Model.type.RefType");
        }else throw new DeclaredExceptions("Undefined variable");
        return null;
    }

    @Override
    public String toString(){return "wH(" + var_name + " = " + expression.toString() + ")";}

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType type_var = typeEnv.lookup(var_name);
        IType type_exp = expression.typeCheck(typeEnv);
        if(type_var.equals(new RefType(type_exp))){
            return typeEnv;
        }else throw new DeclaredExceptions("Write Heap stmt: right hand side and left hand side have different types");
    }
}
