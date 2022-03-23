package model.adt;

import util.AddressBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LatchTable implements ILatchTable{
    private IDict<Integer, Integer> _map;
    private AddressBuilder addressBuilder = new AddressBuilder();

    public LatchTable(){
        this._map = new MyDict<>();
    }

    @Override
    public void add(Integer v1, Integer v2) {
        this._map.add(v1, v2);

    }

    @Override
    public void remove(Integer id) {
        this._map.remove(id);
    }

    @Override
    public int lookup(Integer id) {
        return this._map.lookup(id);
    }

    @Override
    public IDict<Integer, Integer> getContent() {
        return this._map;
    }

    @Override
    public Collection<Integer> getValues() {
        return this._map.getContent().values();
    }

    @Override
    public Set<Integer> keySet() {
        return this._map.keySet();
    }

    @Override
    public Integer getfreeLoc() {
        return addressBuilder.getAddress();
    }

    @Override
    public boolean isDefined(Integer id) {
        return this._map.isDefined(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry: _map.getContent().entrySet()) {
            Integer key = entry.getKey();
            Integer val = entry.getValue();
            stringBuilder.append(String.format("%s -> %s\n", key.toString(), val.toString()));
        }
        return stringBuilder.toString();
    }
}
