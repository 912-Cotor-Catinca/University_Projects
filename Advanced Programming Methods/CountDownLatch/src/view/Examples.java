package view;

import model.expressions.*;
import model.statements.*;
import model.statements.Barrier.newBarrier;
import model.statements.Latch.awaitStmt;
import model.statements.Latch.countDown;
import model.statements.Latch.newLatch;
import model.statements.Lock.LockStatement;
import model.statements.Lock.NewLockStmt;
import model.statements.Lock.UnlockStmt;
import model.statements.Semaphore.Acquire;
import model.statements.Semaphore.ReleaseStmt;
import model.statements.Semaphore.createSemaphore;
import model.statements.ToySem.acquire;
import model.statements.ToySem.newSem;
import model.statements.ToySem.release;
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


        IStmt example10 = new CompStmt(new VarDecStmt("v", new IntType()),
                new CompStmt( new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),new ForStmt("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)), new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), '+'),
                        new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), '+')))))),
                        new PrintStmt(new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(10)), '*'))));

        IStmt example11 = new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("v", new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')),
                        new CompStmt(new AssignStmt("v", new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')),
                                new PrintStmt(new VarExp("v"))))),
                        new CompStmt(new SleepStmt(10), new PrintStmt(new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(10)), '*'))))));

        IStmt example12 = new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt( new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))), new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(3)), "<"),
                new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                        new AssignStmt("v", new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), '+')))), new AssignStmt("v", new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), '+'))))),
                new CompStmt(new SleepStmt(5),new PrintStmt(new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(10)), '*')))));

        IStmt example13 = new CompStmt(new VarDecStmt("v", new IntType()),
                new CompStmt(new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))), new WaitStmt(10)), new PrintStmt(new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(10)), '*'))));

        IStmt example14 = new CompStmt(new VarDecStmt("a", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                new CompStmt(new VarDecStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))), new CompStmt(new VarDecStmt("c", new IntType()),
                        new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))), new CompStmt(new Switch(new ArithmeticExp(new VarExp("a"), new ValueExp(new IntValue(10)), '*'),
                                new ArithmeticExp(new VarExp("b"), new VarExp("c"), '*'), new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                new ValueExp(new IntValue(10)), new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(300)))),
                                new PrintStmt(new ValueExp(new IntValue(300)))), new PrintStmt(new ValueExp(new IntValue(300))))))))));


        IStmt example15 = new CompStmt(new VarDecStmt("a", new RefType(new IntType())), new CompStmt(new VarDecStmt("b", new RefType(new IntType())),
                new CompStmt(new VarDecStmt("v", new IntType()), new CompStmt(new NewStmt("a", new ValueExp(new IntValue(0))),
                        new CompStmt(new NewStmt("b", new ValueExp(new IntValue(0))), new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(1))),
                                new CompStmt(new WriteHeapStmt("b", new ValueExp(new IntValue(2))), new CompStmt(new CondStmt("v", new RelationalExp(new ReadHeapExp(new VarExp("a")), new ReadHeapExp(new VarExp("b")), "<"),
                                        new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))), new CompStmt(new PrintStmt(new VarExp("v")),
                                        new CompStmt(new CondStmt("v", new RelationalExp(new ArithmeticExp(new ReadHeapExp(new VarExp("b")),new ValueExp(new IntValue(2)), '-'),
                                                new ReadHeapExp(new VarExp("a")), ">"), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                new PrintStmt(new VarExp("v"))))))))))));


        IStmt example16 = new CompStmt(new VarDecStmt("v1", new RefType(new IntType())), new CompStmt(new VarDecStmt("cnt", new IntType()),
                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(1))), new CompStmt(new createSemaphore("cnt", new ReadHeapExp(new VarExp("v1"))),
                        new CompStmt(new ForkStmt(new CompStmt(new Acquire("cnt"), new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), '*')),
                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))), new ReleaseStmt("cnt"))))), new CompStmt(new ForkStmt(new CompStmt(
                                        new Acquire("cnt"), new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), '*')),
                                        new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(2)), '*')), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                new ReleaseStmt("cnt")))))), new CompStmt(new Acquire("cnt"), new CompStmt(new PrintStmt(new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)), '-')),
                                                        new ReleaseStmt("cnt")))))))));


        IStmt fork1 = new CompStmt(
                new WriteHeapStmt(
                        "v1",
                        new ArithmeticExp( new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), '*')
                ),
                new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                        new countDown("cnt")
                )
        );


        IStmt fork2 = new CompStmt(
                new WriteHeapStmt(
                        "v2",
                        new ArithmeticExp( new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)), '*')
                ),
                new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                        new countDown("cnt")
                )
        );
        IStmt fork3 = new CompStmt(
                new WriteHeapStmt(
                        "v3",
                        new ArithmeticExp( new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(10)), '*')
                ),
                new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v3"))),
                        new countDown("cnt")
                )
        );
        IStmt ex17 = new CompStmt(new VarDecStmt("cnt", new IntType()), new CompStmt(
                new VarDecStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDecStmt("v2", new RefType(new IntType())),
                        new CompStmt(
                                new VarDecStmt("v3", new RefType(new IntType())),
                                new CompStmt(
                                        new NewStmt("v1", new ValueExp(new IntValue(2))),
                                        new CompStmt(
                                                new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                new CompStmt(
                                                        new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                        new CompStmt(
                                                                new newLatch("cnt", new ReadHeapExp(new VarExp("v2"))),
                                                                new CompStmt(
                                                                        new ForkStmt(fork1),
                                                                        new CompStmt(
                                                                                new ForkStmt(fork2),
                                                                                new CompStmt(
                                                                                        new ForkStmt(fork3),
                                                                                        new CompStmt(
                                                                                                new awaitStmt("cnt"),
                                                                                                new CompStmt(
                                                                                                        new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                                        new CompStmt(
                                                                                                                new countDown("cnt"),
                                                                                                                new PrintStmt(new ValueExp(new IntValue(100)))
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        )
        );


        IStmt fork12 = new ForkStmt(new CompStmt(new model.statements.Barrier.awaitStmt("cnt"),
                new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp( new ReadHeapExp(new VarExp("v1")),
                        new ValueExp(new IntValue(10)), '*')), new PrintStmt(new ReadHeapExp(new VarExp("v1"))))));

        IStmt fork23 = new ForkStmt(new CompStmt(new model.statements.Barrier.awaitStmt("cnt"),
                new CompStmt(new WriteHeapStmt("v2", new ArithmeticExp( new ReadHeapExp(new VarExp("v2")),
                        new ValueExp(new IntValue(10)), '*')), new CompStmt(
                        new WriteHeapStmt("v2", new ArithmeticExp( new ReadHeapExp(new VarExp("v2")),
                                new ValueExp(new IntValue(10)), '*')),
                        new PrintStmt(new ReadHeapExp(new VarExp("v2")))
                ))));

        IStmt ex18 = new CompStmt(new VarDecStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDecStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDecStmt("v3", new RefType(new IntType())),
                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                new CompStmt(new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                        new CompStmt(new VarDecStmt("cnt", new IntType()),
                                                                new CompStmt(new newBarrier("cnt", new ReadHeapExp(new VarExp("v2"))),
                                                                        new CompStmt(fork12, new CompStmt(fork23,
                                                                                new CompStmt(new model.statements.Barrier.awaitStmt("cnt"),
                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v3"))))))))
                                                ))))));

        IStmt example19 = new CompStmt(new VarDecStmt("v1", new IntType()), new CompStmt(new VarDecStmt("v2", new IntType()),
                new CompStmt(new AssignStmt("v1", new ValueExp(new IntValue(2))), new CompStmt(new AssignStmt("v2", new ValueExp(new IntValue(3))),
                        new IfStmt(new VarExp("v1"), new PrintStmt(new MUL(new VarExp("v1"), new VarExp("v2"))), new PrintStmt(new VarExp("v1")))))));


        IStmt example20 = new CompStmt(new VarDecStmt("v1", new RefType(new IntType())), new CompStmt(
                new VarDecStmt("v2", new RefType(new IntType())), new CompStmt( new VarDecStmt("x", new IntType()),
                new CompStmt(new VarDecStmt("q", new IntType()), new CompStmt(new NewStmt("v1", new ValueExp(
                        new IntValue(20))), new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
                        new CompStmt(new NewLockStmt("x"), new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(
                                new CompStmt(new LockStatement("x"), new CompStmt(new WriteHeapStmt("v1",
                                        new ArithmeticExp( new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)), '-')),
                                        new UnlockStmt("x")))), new CompStmt(new LockStatement("x"), new CompStmt(new WriteHeapStmt("v1",
                                new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), '*')),
                                new UnlockStmt("x"))))

                        ), new CompStmt(new NewLockStmt("q"), new CompStmt(
                                new ForkStmt(new CompStmt(new ForkStmt(
                                        new CompStmt(new LockStatement("q"), new CompStmt(new WriteHeapStmt("v2",
                                                new ArithmeticExp(new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(5)), '+')),
                                                new UnlockStmt("q")))), new CompStmt(new LockStatement("q"), new CompStmt(new WriteHeapStmt("v2",
                                        new ArithmeticExp( new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)), '*')),
                                        new UnlockStmt("q"))))), new CompStmt( new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),
                                new CompStmt(new NopStmt(), new CompStmt(new LockStatement("x"), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                        new CompStmt(new UnlockStmt("x"), new CompStmt(new LockStatement("q"), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                new UnlockStmt("q")))))))))

                        )))))))
                ))));


        IStmt fork13 = new ForkStmt(new CompStmt(new acquire("cnt"),
                new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")),
                        new ValueExp(new IntValue(10)), '*')), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                        new release("cnt")))));

        IStmt fork24 = new ForkStmt(new CompStmt(new acquire("cnt"),
                new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")),
                        new ValueExp(new IntValue(10)), '*')), new CompStmt(
                        new WriteHeapStmt("v1", new ArithmeticExp( new ReadHeapExp(new VarExp("v1")),
                                new ValueExp(new IntValue(2)), '*')), new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                        new release("cnt"))))));

        IStmt example21 = new CompStmt(new VarDecStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDecStmt("cnt", new IntType()),
                        new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
                                new CompStmt(new newSem("cnt", new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1))),
                                        new CompStmt(fork13, new CompStmt(fork24,
                                                new CompStmt(new acquire("cnt"),
                                                        new CompStmt(new PrintStmt(new ArithmeticExp(
                                                                new ReadHeapExp(new VarExp("v1")),
                                                                new ValueExp(new IntValue(1)), '-')),
                                                                new release("cnt")))))))));

        IStmt example22 = new CompStmt(new VarDecStmt("a", new RefType(new IntType())), new CompStmt(new VarDecStmt("v", new IntType()),
                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(20))), new CompStmt(
                        new ForStmt("v", new ValueExp(new IntValue(0)),  new ValueExp(new IntValue(3)),
                                new ArithmeticExp(new VarExp("v"), new ValueExp(new IntValue(1)), '+'),
                                        new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithmeticExp(new VarExp("v"), new ReadHeapExp(new VarExp("a")),'*'))))),
                                                new PrintStmt(new ReadHeapExp(new VarExp("a")))))));


        return new IStmt[]{example0, example1, example2, example3, example4, example5, example6, example7, example8, example9, example10, example11, example12, example13, example14, example15, example16, ex17, ex18, example19, example20, example21, example22};
    }
}
