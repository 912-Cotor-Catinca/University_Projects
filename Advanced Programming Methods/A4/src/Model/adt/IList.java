package Model.adt;

import Exceptions.ADTExceptions;

import java.util.List;

public interface IList<T> {
    void add(T val);
    T get(int index);
    int size();
    String toString();
    T pop() throws ADTExceptions;
    boolean isEmpty();
    List<T> getAll();
}
