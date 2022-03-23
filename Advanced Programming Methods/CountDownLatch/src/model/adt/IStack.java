package model.adt;

import java.util.Stack;

public interface IStack<T> {
    void push(T v);
    T pop();
    boolean isEmpty();
    public Stack<T> getStack();
    String toString();
}
