package model.adt;

import java.util.ListIterator;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private final Stack<T> stack;

    public MyStack(){
        stack = new Stack<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public Stack<T> getStack() {
        return stack;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ListIterator<T> stackIterator = stack.listIterator(stack.size());
        while (stackIterator.hasPrevious()) {
            stringBuilder.append(stackIterator.previous().toString()).append('\n');
        }
        return stringBuilder.toString();
    }
}
