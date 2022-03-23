package model.adt;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface ILatchTable {
    void add(Integer v1, Integer v2);
    void remove(Integer id);
    int lookup(Integer id);
    IDict<Integer, Integer> getContent();
    Collection<Integer> getValues();
    Set<Integer> keySet();
    Integer getfreeLoc();
    boolean isDefined(Integer id);
}
