package model.adt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyHeap<V> implements IHeap<V> {
    private Map<Integer,V> heapTable;
    int freeLocation = 1;

    public MyHeap() {
        heapTable = new ConcurrentHashMap<>();
    }

    @Override
    public Integer getFreeLocation() {
        return freeLocation;
    }

    @Override
    public Map<Integer, V> getContent() {
        return heapTable;
    }

    @Override
    public void setContent(Map<Integer, V> newContent) {
        heapTable = newContent;
    }

    @Override
    public Integer add(V value) {
        heapTable.put(freeLocation++, value);
        return freeLocation-1;
    }

    @Override
    public void remove(Integer key) {
        heapTable.remove(key);
    }

    @Override
    public void update(Integer key, V value) {
        heapTable.put(key, value);
    }

    @Override
    public boolean exists(Integer key) {
        return heapTable.containsKey(key);
    }

    @Override
    public V get(Integer key) {
        return heapTable.get(key);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, V> entry: heapTable.entrySet()) {
            Integer key = entry.getKey();
            V val = entry.getValue();
            stringBuilder.append(String.format("%s -> %s\n", key.toString(), val.toString()));
        }
        return stringBuilder.toString();
    }
}
