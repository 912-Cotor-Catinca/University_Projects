package Model.stmt;

import Exceptions.DeclaredExceptions;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.exp.Exp;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.io.BufferedReader;

public class ReadFileStmt implements Model.stmt.IStmt {
    private final Exp exp;
    private final String var_name;

    public ReadFileStmt(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public String toString(){
        return "(read_file '"+this.exp +"')";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symtbl = state.getSymTable();
        IDict<StringValue, BufferedReader> fileTbl = state.getFileTable();
        IHeap<Integer, IValue> heap = state.getHeapTable();
        if(symtbl.containsKey(var_name)){
            IValue v = symtbl.lookup(var_name);
            if(v.getType().equals(new IntType())){
                IValue cond = exp.eval(symtbl, heap);
                if(cond.getType().equals(new StringType())){
                    StringValue s = (StringValue) cond;
                    if(fileTbl.containsKey(s)){
                        BufferedReader reader = fileTbl.lookup(s);
                        String line = reader.readLine();
                        if(line== null){
                            symtbl.update(var_name, new IntValue(0));
                        }else{
                            symtbl.update(var_name, new IntValue(Integer.parseInt(line)));
                        }
                    }else throw new Exception("file not defined");
                }else throw new DeclaredExceptions("variable type is not string");
            }else throw new DeclaredExceptions("variable type is not int");
        }else throw new DeclaredExceptions("variable undefined");

        return state;
    }
}
