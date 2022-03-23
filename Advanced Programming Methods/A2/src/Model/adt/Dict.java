package Model.adt;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dict<T1, T2> implements IDict<T1, T2>{
    private Map<T1, T2> dictionary;

    public Dict(){
        this.dictionary = new HashMap<T1, T2>();
    }

    @Override
    public void add(T1 v1, T2 v2) {
        this.dictionary.put(v1, v2);
    }

    @Override
    public T2 lookup(T1 id) {
        return this.dictionary.get(id);
    }

    @Override
    public boolean containsKey(T1 id) {
        return this.dictionary.containsKey(id);
    }

    @Override
    public T2 getValue(T1 id) {
        return this.dictionary.get(id);
    }

    @Override
    public void update(T1 id, T2 value) {
        this.dictionary.replace(id, value);
    }

    @Override
    public T2 put(T1 id, T2 val) {
        return this.dictionary.put(id, val);
    }

    @Override
    public String toString(){
        return this.dictionary.toString();
    }

    @Override
    public Set<T1> keys() {
        return this.dictionary.keySet();
    }

    @Override
    public void remove(T1 id) {
        this.dictionary.remove(id);
    }
}

