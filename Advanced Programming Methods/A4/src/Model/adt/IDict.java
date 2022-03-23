package Model.adt;

import java.util.*;

public interface IDict<T1, T2> {

    void add(T1 v1, T2 v2);
    T2 lookup(T1 id);
    boolean containsKey(T1 id);
    T2 getValue(T1 id);
    void update(T1 id, T2 value);
    T2 put(T1 id, T2 val);
    String toString();
    //String toFile();
    Set<T1> keys();

    void remove(T1 id);

    Map<T1,T2> getContent();
}
