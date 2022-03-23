package Model.adt;

import Model.value.IValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Heap<T1, T2> implements IHeap<T1, T2>{
    private Integer address;
    private Map<Integer, T2> map;

    public Heap(){
        this.map = new HashMap<Integer, T2>();
        this.address = 1;
    }

    @Override
    public void setContent(Map<Integer, T2> newMap) {
            map.clear();
            for(Integer i : newMap.keySet())
                map.put(i, newMap.get(i));

    }

    @Override
    public Map<Integer, T2> getContent() {
            return map;
    }

    @Override
    public void update(Integer pos, T2 val) throws Exception {
            if (!map.containsKey(pos))
                throw new Exception("the key does not exists");
            map.put(pos, val);
    }

    public void getNextFreeAddress(){
            this.address++;
    }

    @Override
    public Integer getAddress(T2 v) {
            Integer key = 0;
            if (!this.map.isEmpty())
                key = 1;
            Iterator it = this.map.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<Integer, T2> entry = (Map.Entry) it.next();
                if (entry.getValue().equals(v)) {
                    key = entry.getKey();
                }
            }
            return key;
    }

    @Override
    public boolean containsKey(int address) {
            return map.containsKey(address);
    }

    @Override
    public T2 lookup(int address) {
            return map.get(address);
    }

    public int add(T2 v){
            if (!this.containsKey(this.address)) {
                int freePos = this.address;
                this.map.put(address, v);
                getNextFreeAddress();
                return freePos;
            }
            return 0;

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
}
