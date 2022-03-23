package Model.adt;

import Exceptions.ADTExceptions;

public interface IStack <T>{
    T pop() throws ADTExceptions;
    void push(T v);
    boolean isEmpty();
    String toString();
    //String toFile();

    IStack<T> deepcopy();
}
