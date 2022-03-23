package View;

import Controller.Controller;
import Model.PrgState;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.types.RefType;
import Model.types.StringType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repository.IRepo;
import Repository.Repo;

import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) throws Exception {

        // ex 1:  int v; v = 2; Print(v)
        IStmt ex1 = new CompStmt(new VarDecl("v", new IntType()),
                new CompStmt(new Model.stmt.AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        IStack<IStmt> exestack = new Model.adt.MyStack<IStmt>();
        exestack.push(ex1);
        IDict<String, IValue> symTable = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> filetbl = new Dict<StringValue, BufferedReader>();
        Model.adt.IList<IValue> out = new MyList<IValue>();
        IHeap heap = new Heap();
        PrgState prg1 = new PrgState(exestack, symTable, out, ex1, filetbl, heap);
        IRepo repo1 = new Repo("log1.txt");
        repo1.addPrg(prg1);
        Controller ctr1 = new Controller(repo1);

        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt(new VarDecl("a", new IntType()), new CompStmt(new VarDecl("b", new IntType()),
                new CompStmt(new Model.stmt.AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(
                        new Model.stmt.AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));
        IStack<IStmt> exestack1 = new Model.adt.MyStack<IStmt>();
        exestack1.push(ex2);
        IDict<String, IValue> symTable1 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> filetbl1 = new Dict<StringValue, BufferedReader>();
        Model.adt.IList<IValue> out1 = new MyList<IValue>();
        IHeap heap1 = new Heap();
        PrgState prg2 = new PrgState(exestack1, symTable1, out1, ex2, filetbl1, heap1);
        IRepo repo2 = new Repo("log2.txt");
        repo2.addPrg(prg2);
        Controller ctr2 = new Controller(repo2);


        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDecl("a", new BoolType()), new CompStmt(new VarDecl("v",
                new IntType()), new CompStmt(new Model.stmt.AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"), new Model.stmt.AssignStmt("v", new ValueExp(new IntValue(2))),
                        new Model.stmt.AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));

        IStack<IStmt> exestack2 = new Model.adt.MyStack<IStmt>();
        exestack2.push(ex3);
        IDict<String, IValue> symTable2 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> filetbl2 = new Dict<StringValue, BufferedReader>();
        Model.adt.IList<IValue> out2 = new MyList<IValue>();
        IHeap heap2 = new Heap();
        PrgState prg3 = new PrgState(exestack2, symTable2, out2, ex3, filetbl2, heap2);
        IRepo repo3 = new Repo("log3.txt");
        repo3.addPrg(prg3);
        Controller ctr3 = new Controller(repo3);

        IStmt stmt4 = new CompStmt(new VarDecl("varf", new StringType()),
                new CompStmt(new Model.stmt.AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDecl("varc", new IntType()),
                                        new CompStmt(new Model.stmt.AssignStmt("varc", new ValueExp(new IntValue())),
                                                new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                        new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                        new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFileStmt(new VarExp("varf")))))))))));


        IStack<IStmt> exestack3 = new Model.adt.MyStack<IStmt>();
        exestack3.push(stmt4);
        IDict<String, IValue> symTable3 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> filetbl3 = new Dict<StringValue, BufferedReader>();
        Model.adt.IList<IValue> out3 = new MyList<IValue>();
        IHeap<Integer, IValue> heap3 = new Heap<Integer, IValue>();
        PrgState prg4 = new PrgState(exestack3, symTable3, out3, stmt4, filetbl3, heap3);
        IRepo repo4 = new Repo("log4.txt");
        repo4.addPrg(prg4);
        Controller ctr4 = new Controller(repo4);

        IStmt ex5 = new CompStmt(new VarDecl("v", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                                new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))), new PrintStmt(new VarExp("v")))));
        IStack<IStmt> exestack4 = new Model.adt.MyStack<IStmt>();
        exestack4.push(ex5);
        IDict<String, IValue> symTable4 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> filetbl4 = new Dict<StringValue, BufferedReader>();
        Model.adt.IList<IValue> out4 = new MyList<IValue>();
        IHeap<Integer, IValue> heap4 = new Heap<Integer, IValue>();
        PrgState prg5 = new PrgState(exestack4, symTable4, out4, ex5, filetbl4, heap4);
        IRepo repo5 = new Repo("log5.txt");
        repo5.addPrg(prg5);
        Controller ctr5 = new Controller(repo5);

        IStmt ex6 = new CompStmt(new VarDecl("v", new RefType(new IntType())), new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))), new CompStmt(new VarDecl("a", new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocStmt("a", new VarExp("v")), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));

        IStack<IStmt> exestack5 = new Model.adt.MyStack<IStmt>();
        exestack5.push(ex6);
        IDict<String, IValue> symTable5 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> filetbl5 = new Dict<StringValue, BufferedReader>();
        Model.adt.IList<IValue> out5 = new MyList<IValue>();
        IHeap<Integer, IValue> heap5 = new Heap<Integer, IValue>();
        PrgState prg6 = new PrgState(exestack5, symTable5, out5, ex6, filetbl5, heap5);
        IRepo repo6 = new Repo("log6.txt");
        repo6.addPrg(prg6);
        Controller ctr6 = new Controller(repo6);

        IStmt ex7 = new CompStmt(new VarDecl("v", new RefType(new IntType())), new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))), new CompStmt(new VarDecl("a", new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocStmt("a", new VarExp("v")), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))), new PrintStmt(new ArithExp('+', new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));

        IStack<IStmt> exestack6 = new Model.adt.MyStack<IStmt>();
        exestack6.push(ex7);
        IDict<String, IValue> symTable6 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> filetbl6 = new Dict<StringValue, BufferedReader>();
        Model.adt.IList<IValue> out6 = new MyList<IValue>();
        IHeap<Integer, IValue> heap6 = new Heap<Integer, IValue>();
        PrgState prg7 = new PrgState(exestack6, symTable6, out6, ex7, filetbl6, heap6);
        IRepo repo7 = new Repo("log7.txt");
        repo7.addPrg(prg7);
        Controller ctr7 = new Controller(repo7);

        IStmt ex8 = new CompStmt(new VarDecl("v", new RefType(new IntType())),
                        new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                        new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithExp('+', new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));

        IStack<IStmt> exestack7 = new Model.adt.MyStack<IStmt>();
        exestack7.push(ex8);
        IDict<String, IValue> symTable7 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> filetbl7 = new Dict<StringValue, BufferedReader>();
        Model.adt.IList<IValue> out7 = new MyList<IValue>();
        IHeap<Integer, IValue> heap7 = new Heap<Integer, IValue>();
        PrgState prg8 = new PrgState(exestack7, symTable7, out7, ex8, filetbl7, heap7);
        IRepo repo8 = new Repo("log8.txt");
        repo8.addPrg(prg8);
        Controller ctr8 = new Controller(repo8);

        IStmt ex9 = new CompStmt(new VarDecl("v", new RefType(new IntType())),
                        new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new VarDecl("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new HeapAllocStmt("a", new VarExp("v")), new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

        IStack<IStmt> exestack8 = new Model.adt.MyStack<IStmt>();
        exestack8.push(ex9);
        IDict<String, IValue> symTable8 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> filetbl8 = new Dict<StringValue, BufferedReader>();
        Model.adt.IList<IValue> out8 = new MyList<IValue>();
        IHeap<Integer, IValue> heap8 = new Heap<Integer, IValue>();
        PrgState prg9 = new PrgState(exestack8, symTable8, out8, ex9, filetbl8, heap8);
        IRepo repo9 = new Repo("log9.txt");
        repo9.addPrg(prg9);
        Controller ctr9 = new Controller(repo9);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", stmt4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));

        menu.show();

    }
}
