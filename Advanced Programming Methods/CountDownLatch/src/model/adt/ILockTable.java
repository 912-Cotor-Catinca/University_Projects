package model.adt;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface ILockTable<V> {
    void add(Integer v1, V v);
    Integer getFreeLocation();
    void update(Integer v1, V v2);
    void remove(Integer id);
    V lookup(Integer id);
    Map<Integer, V> getContent();
    Set<Integer> keySet();
    boolean isDefined(Integer id);
    Collection<V> getValues();
}
