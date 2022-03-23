package Model.adt;

import Model.value.IValue;

import java.util.Map;

public interface IHeap<T1, T2> {

    void setContent(Map<Integer, T2> newMap);
    Map<Integer, T2> getContent();
    int add(T2 v);
    void update(Integer v1, T2 v2) throws Exception;
    void getNextFreeAddress();
    Integer getAddress(T2 v);
    boolean containsKey(int address);
    String toString();
    T2 lookup(int address);
}
