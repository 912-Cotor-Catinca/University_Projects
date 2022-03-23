package model.adt;

import java.util.List;

public interface IList<T> {
    void add(T v);
    T pop();
    boolean isEmpty();
    public List<T> getList();
    String toString();

}
