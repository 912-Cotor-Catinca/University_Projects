package Model;

import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.lang.management.BufferPoolMXBean;

public class PrgState {
    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IList<IValue> out;
    IStmt originalProgram;
    IDict<StringValue, BufferedReader> fileTable;

    public PrgState(IStack<IStmt> stk, IDict<String, IValue> tbl, IList<IValue> out, IStmt prgm, IDict<StringValue, BufferedReader> file){
        this.exeStack = stk;
        this.symTable = tbl;
        this.out = out;
        this.originalProgram = prgm;
        this.fileTable = file;
    }

    public IStack<IStmt> getStack() {
        return this.exeStack;
    }
    public IDict<String, IValue> getSymTable(){
        return this.symTable;
    }
    public IList<IValue> getOut(){
        return this.out;
    }
    public IDict<StringValue, BufferedReader> getFileTable(){return this.fileTable;}

    public void setStack(IStack<IStmt> stk) {this.exeStack = stk;}
    public void setSymTable(IDict<String, IValue> tbl) {this.symTable = tbl;}
    public void setOut(IList<IValue> out) {this.out = out;}

    public String toString(){
        return """
                -----------------START STATE------------
                ----------Execution Stack: --------
                """+ this.exeStack.toString()+
                "\n-------- Symbol Table: --------\n"+
                this.symTable.toString()+
                "\n---------- Output: -----------\n"+
                this.out.toString() +
                "\n---------- File Table -----\n"+
                this.fileTable.toString()+
                "\n------------END STATE-----------\n";

    }

//    public String toFile(){
//        return "Stack: " + this.exeStack.toFile() + "\nSymTable: " + this.symTable.toFile() + "\nOutPut: " +
//                this.out.toFile() + "\n" + "FileTable:" + this.fileTable.toFile() + "\n\n";
//    }
}
