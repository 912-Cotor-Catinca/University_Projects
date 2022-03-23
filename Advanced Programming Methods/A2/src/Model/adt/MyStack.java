package Model.adt;

import Exceptions.ADTExceptions;
import Model.stmt.CompStmt;
import Model.stmt.IStmt;

import java.util.Stack;

public class MyStack<T> implements IStack<T>{
    private Stack<T> stack;

    public MyStack(){
        this.stack = new Stack<T>();
    }
    public MyStack(Stack<T> stk){
        this.stack = stk;
    }

    @Override
    public T pop() throws ADTExceptions {
        if(isEmpty())
            throw new ADTExceptions("Stack is empty");
        return this.stack.pop();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }


    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public String inOrderTraversal(IStmt stmt){
        if (stmt instanceof CompStmt) {
            String returnString = "";

            CompStmt newStmt = (CompStmt) stmt;
            IStmt left = newStmt.getFirst();
            IStmt right = newStmt.getSecond();

            if (left instanceof CompStmt) {
                returnString += inOrderTraversal(left) + "\n";
            }
            else {
                returnString += left.toString() + "\n";
            }

            if (right instanceof CompStmt) {
                returnString += inOrderTraversal(right) + "\n";
            }
            else {
                returnString += right.toString() + "\n";
            }

            return returnString;
        }
        else {
            return stmt.toString();
        }
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        Stack<T> printStack = new Stack<>();

        printStack.addAll(this.stack);
        for(T elem : printStack){
            if(elem instanceof IStmt){
                stringBuilder.append(inOrderTraversal((IStmt) elem));
            }
            else{
                stringBuilder.append(elem.toString());
            }
        }
        return stringBuilder.toString();

    }

    @Override
    public IStack<T> deepcopy() {
        return new MyStack<T>(this.stack);
    }
}
