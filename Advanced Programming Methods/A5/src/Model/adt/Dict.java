package Model.adt;

import Model.value.IValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Dict<T1, T2> implements Model.adt.IDict<T1, T2> {
    protected Map<T1, T2> dictionary;

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
        StringBuilder result = new StringBuilder();
        Iterator it = this.getContent().entrySet().iterator();
        Map.Entry entry;
        while(it.hasNext()){
            entry = (Map.Entry) it.next();
            result.append("Key: ").append((String) entry.getKey().toString()).append(", Value: ").append((IValue) entry.getValue());
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public Set<T1> keys() {
            return this.dictionary.keySet();

    }

    @Override
    public void remove(T1 id) {
            this.dictionary.remove(id);

    }

    @Override
    public Map<T1, T2> getContent() {
            return this.dictionary;
    }

    @Override
    public IDict<T1, T2> copy() {
        Dict<T1, T2> toReturn = new Dict<>();
        toReturn.setContent(this.getContent());
        return toReturn;
    }

    public void setContent(Map<T1, T2> newmap){
        this.dictionary.clear();
        for (Map.Entry<T1, T2> entry : newmap.entrySet()){
            this.dictionary.put(entry.getKey(), entry.getValue());
        }
    }
}

