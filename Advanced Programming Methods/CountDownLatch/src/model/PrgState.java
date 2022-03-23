package model;

import exceptions.PrgStateException;
import model.adt.*;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDict<String, IValue> symTable;
    private IDict<StringValue, BufferedReader> fileTable;
    private IHeap<IValue> heapTable;
    private IList<String> out;
    private ILockTable<Integer> lockTable;
    private ISemaphore semaphore;
    private IBarrier barrier;
    private IToySem toySem;
    private ILatchTable latchTable;
    private IStmt originalProgram;
    public Integer id = 1;
    public static Integer lastID = 1;
    public Integer address = 1;
    public static IStack<Integer> freeAddress = new MyStack<>();


    public PrgState(IStmt originalProgram) {
        exeStack = new MyStack<>();
        symTable = new MyDict<>();
        fileTable = new MyDict<>();
        heapTable = new MyHeap<>();
        out = new MyList<>();
        semaphore = new Semaphore();
        lockTable = new LockTable<>();
        latchTable = new LatchTable();
        barrier = new Barrier();
        toySem = new TSem();
        this.originalProgram = originalProgram.deepCopy();
        exeStack.push(originalProgram);
    }

    public PrgState(IStack<IStmt> exeStack, IDict<String, IValue> symTable, IDict<StringValue, BufferedReader> fileTable, IHeap<IValue> heapTable, IList<String> out,
                    ILockTable<Integer> lockTable, ISemaphore semaphore, ILatchTable latchTable, IBarrier barrier, IToySem sem,IStmt originalProgram) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.heapTable = heapTable;
        this.fileTable = fileTable;
        this.lockTable = lockTable;
        this.semaphore = semaphore;
        this.latchTable = latchTable;
        this.barrier = barrier;
        this.toySem = sem;
        this.originalProgram = originalProgram.deepCopy();
        exeStack.push(originalProgram);
    }

    public PrgState(IStack<IStmt> exeStack, IDict<String, IValue> symTable, IDict<StringValue, BufferedReader> fileTable, IHeap<IValue> heapTable, IList<String> out, ILockTable<Integer> lockTable, ISemaphore semaphore,ILatchTable latchTable, IBarrier barrier, IToySem sem) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.heapTable = heapTable;
        this.fileTable = fileTable;
        this.lockTable = lockTable;
        this.semaphore = semaphore;
        this.latchTable = latchTable;
        this.barrier = barrier;
        this.toySem = sem;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws Exception {
        if (exeStack.isEmpty()) {
            throw new PrgStateException("Execution stack is empty!");
        }
        IStmt currentStmt = exeStack.pop();
        return currentStmt.execute(this);
    }

    public synchronized void setID() {
        lastID++;
        id = lastID;
    }

    public ISemaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(IDict<Integer, Pair<Integer, List<Integer>>> semaphore) {
        this.semaphore.setSemaphore(semaphore);
    }

    public IToySem getToySem() {
        return toySem;
    }

    public void setToySem(IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> toySem) {
        this.toySem.setTSemaphore(toySem);
    }

    public IBarrier getBarrier() {
        return barrier;
    }

    public void setBarrier(IDict<Integer, Pair<Integer, List<Integer>>> barrier) {
        this.barrier.setBarrier(barrier);
    }

    public ILatchTable getLatchTable() {
        return latchTable;
    }

    public void setLatchTable(ILatchTable latchTable) {
        this.latchTable = latchTable;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAddress() {
        return freeAddress.isEmpty() ? this.address++ : freeAddress.pop();
    }

    public ILockTable<Integer> getLockTable() {
        return lockTable;
    }

    public void setLockTable(ILockTable<Integer> lockTable) {
        this.lockTable = lockTable;
    }

    public PrgState deepCopy() {
        return new PrgState(exeStack, symTable, fileTable, heapTable, out, lockTable, semaphore, latchTable, barrier, toySem, originalProgram);
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(IStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public IDict<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(IDict<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public IList<String> getOut() {
        return out;
    }

    public void setOut(IList<String> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public IDict<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IDict<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IHeap<IValue> getHeapTable() {
        return heapTable;
    }

    public void setHeapTable(IHeap<IValue> heapTable) {
        this.heapTable = heapTable;
    }

    @Override
    public String toString() {
        return String.format("ID: %s\nExecution stack:\n%s\nSymbol table:\n%s\nHeap table:\n%s\nOutput:\n%s\nFile table:\n%s\n------------------------", id, exeStack, symTable, heapTable, out, fileTable.keysToString());
    }
}
