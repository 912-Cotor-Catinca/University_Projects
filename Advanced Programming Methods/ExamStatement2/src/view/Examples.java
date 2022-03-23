package view;

import model.expressions.*;
import model.statements.*;

import model.statements.Semaphore.Acquire;
import model.statements.Semaphore.ReleaseStmt;
import model.statements.Semaphore.createSemaphore;

import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

public class Examples {
    public static IStmt[] getExamples() {
        IStmt example0 = new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt(
                new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        IStmt example1 = new CompStmt(new VarDecStmt("a", new IntType()), new CompStmt(
                new VarDecStmt("b", new IntType()),  new CompStmt(
                new AssignStmt("a", new ArithmeticExp(new ValueExp(new IntValue(2)), new ArithmeticExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), '*'), '+')), new CompStmt(
                new AssignStmt("b", new ArithmeticExp(new VarExp("a"), new ValueExp(new IntValue(1)), '+')), new PrintStmt(new VarExp("b"))))));
        IStmt example2 = new CompStmt(new VarDecStmt("a", new BoolType()), new CompStmt(
                new VarDecStmt("v", new IntType()), new CompStmt(
                new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(
                new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        IStmt example3 = new CompStmt(new VarDecStmt("varF", new StringType()), new CompStmt(
                new AssignStmt("varF" ,new ValueExp(new StringValue("test.in"))), new CompStmt(
                new OpenRFileStmt(new VarExp("varF")), new CompStmt(
                new VarDecStmt("varC",new IntType()), new CompStmt(
                new ReadFileStmt(new VarExp("varF"), "varC"), new CompStmt(
                new PrintStmt(new VarExp("varC")), new CompStmt(
                new ReadFileStmt(new VarExp("varF"), "varC"), new CompStmt(
                new PrintStmt(new VarExp("varC")),new CloseRFileStmt(new VarExp("varF"))))))))));
        IStmt example4 = new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(
                new NewStmt("v", new ValueExp(new IntValue(20))), new CompStmt(
                new VarDecStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                new NewStmt("a", new VarExp("v")), new CompStmt(
                new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        IStmt example5 = new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(
                new NewStmt("v", new ValueExp(new IntValue(20))), new CompStmt(
                new VarDecStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                new NewStmt("a", new VarExp("v")), new CompStmt(
                new PrintStmt(new ReadHeapExp(new VarExp("v"))), new PrintStmt(new ArithmeticExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)), '+')))))));
        IStmt example6 = new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(
                new NewStmt("v", new ValueExp(new IntValue(20))), new CompStmt(
                new PrintStmt(new ReadHeapExp(new VarExp("v"))), new CompStmt(
                new WriteHeapStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithmeticExp(new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)), '+'))))));
        IStmt example7 = new CompStmt(new VarDecStmt("v", new RefType(new IntType())), new CompStmt(
                new NewStmt("v", new ValueExp(new IntValue(20))), new CompStmt(
                new VarDecStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                new NewStmt("a", new VarExp("v")), new CompStmt(
                new NewStmt("v", new ValueExp(new IntValue(30))), new PrintStmt((new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))))))));
        IStmt example8 = new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt(new CompStmt(
                new AssignStmt("v", new ValueExp(new IntValue(4))), new WhileStmt(
                new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"), new CompStmt(
                new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-'))))), new PrintStmt(new VarExp("v"))));
        IStmt example9 = new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt(
                new VarDecStmt("a", new RefType(new IntType())), new CompStmt(
                new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(
                new NewStmt("a", new ValueExp(new IntValue(22))), new CompStmt(
                new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))), new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(
                        new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a"))))))), new CompStmt(
                new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));


        IStmt example30 = new CompStmt(new VarDecStmt("a", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                new CompStmt(new VarDecStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))), new CompStmt(new VarDecStmt("c", new IntType()),
                        new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))), new CompStmt(new SwitchStmt(new ArithmeticExp(new VarExp("a"), new ValueExp(new IntValue(10)), '*'),
                                new ArithmeticExp(new VarExp("b"), new VarExp("c"), '*'), new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                new ValueExp(new IntValue(10)), new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(300)))),
                                new PrintStmt(new ValueExp(new IntValue(300)))), new PrintStmt(new ValueExp(new IntValue(300))))))))));



        IStmt example16 = new CompStmt(new VarDecStmt("v1", new RefType(new IntType())), new CompStmt(new VarDecStmt("cnt", new IntType()),
                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(1))), new CompStmt(new createSemaphore("cnt", new ReadHeapExp(new VarExp("v1"))),
                        new CompStmt(new ForkStmt(new CompStmt(new Acquire("cnt"), new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), '*')),
                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))), new ReleaseStmt("cnt"))))), new CompStmt(new ForkStmt(new CompStmt(
                                        new Acquire("cnt"), new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), '*')),
                                        new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(2)), '*')), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                new ReleaseStmt("cnt")))))), new CompStmt(new Acquire("cnt"), new CompStmt(new PrintStmt(new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)), '-')),
                                                        new ReleaseStmt("cnt")))))))));



        return new IStmt[]{example0, example1, example2, example3, example4, example5, example6, example7, example8, example9, example30, example16};
    }
}
