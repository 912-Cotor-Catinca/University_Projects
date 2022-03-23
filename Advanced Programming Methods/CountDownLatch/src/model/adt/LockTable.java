package model.adt;

import util.AddressBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LockTable<V> implements ILockTable< V> {
    private final Map<Integer, V> lookTable;
    private AddressBuilder addressBuilder = new AddressBuilder();

    public LockTable() {
        this.lookTable = new HashMap<>();
    }


    @Override
    public void add(Integer v1, V o2) {
        lookTable.put(v1, o2);
    }

    @Override
    public Integer getFreeLocation() {
        return addressBuilder.getAddress();
    }

    @Override
    public void update(Integer v1, V v2) {
        lookTable.put(v1, v2);
    }

    @Override
    public void remove(Integer id) {
        lookTable.remove(id);
    }

    @Override
    public V lookup(Integer id) {
        return lookTable.get(id);
    }

    @Override
    public Map<Integer, V> getContent() {
        return lookTable;
    }

    @Override
    public Set<Integer> keySet() {
        return lookTable.keySet();
    }

    @Override
    public boolean isDefined(Integer id) {
        return lookTable.containsKey(id);
    }

    @Override
    public Collection<V> getValues() {
        return lookTable.values();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, V> entry: lookTable.entrySet()) {
            Integer key = entry.getKey();
            V val = entry.getValue();
            stringBuilder.append(String.format("%s -> %s\n", key.toString(), val.toString()));
        }
        return stringBuilder.toString();
    }
}
