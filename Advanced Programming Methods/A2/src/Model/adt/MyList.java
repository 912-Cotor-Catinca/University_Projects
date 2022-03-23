package Model.adt;

import Exceptions.ADTExceptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyList<T> implements IList<T>{
    private List<T> mylist;
    public MyList(){
        this.mylist = new ArrayList<T>();
    }
    @Override
    public void add(T val) {
        this.mylist.add(val);
    }

    @Override
    public T get(int index) {
        return this.mylist.get(index);
    }

    @Override
    public int size() {
        return this.mylist.size();
    }
    @Override
    public String toString(){
        return this.mylist.toString();
    }

    @Override
    public T pop() throws ADTExceptions {
        if(isEmpty())
            throw new ADTExceptions("The list is empty");
        return this.mylist.remove(this.mylist.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return this.mylist.isEmpty();
    }

    @Override
    public List<T> getAll() {
        return this.mylist;
    }
}
