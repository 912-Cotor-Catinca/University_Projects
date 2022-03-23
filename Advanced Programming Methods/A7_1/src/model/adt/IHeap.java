package model.adt;

import java.util.Map;

public interface IHeap<V> {
    Integer getFreeLocation();
    Map<Integer, V> getContent();
    void setContent(Map<Integer, V> newContent);
    Integer add(V value);
    void remove(Integer key);
    void update(Integer key, V value);
    boolean exists(Integer key);
    V get(Integer key);
}
