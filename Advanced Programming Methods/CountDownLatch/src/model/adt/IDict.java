package model.adt;

import java.util.Map;
import java.util.Set;

public interface IDict<K, V> {
    void add(K v1, V v2);
    void update(K v1, V v2);
    void remove(K id);
    V lookup(K id);
    Map<K, V> getContent();
    Set<K> keySet();
    boolean isDefined(K id);
    String keysToString();
}
