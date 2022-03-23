package Model;

import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IList<IValue> out;
    IStmt originalProgram;
    IDict<StringValue, BufferedReader> fileTable;
    IHeap<Integer, IValue> heapTable;

    public PrgState(Model.adt.IStack<IStmt> stk, Model.adt.IDict<String, IValue> tbl, Model.adt.IList<IValue> out, IStmt prgm, Model.adt.IDict<StringValue, BufferedReader> file,
                    IHeap<Integer, IValue> heap){
        this.exeStack = stk;
        this.symTable = tbl;
        this.out = out;
        this.originalProgram = prgm;
        this.fileTable = file;
        this.heapTable = heap;
    }

    public Model.adt.IStack<IStmt> getStack() {
        return this.exeStack;
    }
    public Model.adt.IDict<String, IValue> getSymTable(){
        return this.symTable;
    }
    public Model.adt.IList<IValue> getOut(){
        return this.out;
    }
    public Model.adt.IDict<StringValue, BufferedReader> getFileTable(){return this.fileTable;}
    public IHeap<Integer, IValue> getHeapTable() {
        return this.heapTable;
    }

    public void setStack(Model.adt.IStack<IStmt> stk) {this.exeStack = stk;}
    public void setSymTable(Model.adt.IDict<String, IValue> tbl) {this.symTable = tbl;}
    public void setOut(Model.adt.IList<IValue> out) {this.out = out;}

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
                "\n------------Heap-----------\n"+
                this.getHeapTable().toString()+
                "\n------------END STATE-----------\n";

    }



//    public String toFile(){
//        return "Stack: " + this.exeStack.toFile() + "\nSymTable: " + this.symTable.toFile() + "\nOutPut: " +
//                this.out.toFile() + "\n" + "FileTable:" + this.fileTable.toFile() + "\n\n";
//    }
}
