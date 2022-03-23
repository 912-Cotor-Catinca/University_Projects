package Model;

import Exceptions.ExecutionExceptions;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.util.Random;
import java.util.TreeSet;

public class PrgState {
    Model.adt.IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    Model.adt.IList<IValue> out;
    IStmt originalProgram;
    IDict<StringValue, BufferedReader> fileTable;
    IHeap<Integer, IValue> heapTable;
    public static int threadCount = 1;
    public final int id;

    private  synchronized static Integer newId(){
        PrgState.threadCount += 1;
        return PrgState.threadCount - 1;
    }

    public PrgState(IStack<IStmt> stk, IDict<String, IValue> tbl, IList<IValue> out, IStmt prgm, IDict<StringValue, BufferedReader> file,
                    IHeap<Integer, IValue> heap){
        this.exeStack = stk;
        this.symTable = tbl;
        this.out = out;
        this.originalProgram = prgm;
        this.fileTable = file;
        this.heapTable = heap;
        id = newId();
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

    public Integer getId(){return this.id;}

    public void setStack(Model.adt.IStack<IStmt> stk) {this.exeStack = stk;}
    public void setSymTable(Model.adt.IDict<String, IValue> tbl) {this.symTable = tbl;}
    public void setOut(Model.adt.IList<IValue> out) {this.out = out;}

    public PrgState oneStep() throws Exception {
        if(this.exeStack.isEmpty())
            throw new ExecutionExceptions("Program state stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public boolean isNotComplete(){
        return !exeStack.isEmpty();
    }

    public String toString(){
        return """
                -----------------START STATE------------
                ---------------------ID-----------------
                """+this.id+
                "\n----------Execution Stack: --------\n"
                + this.exeStack.toString()+
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
