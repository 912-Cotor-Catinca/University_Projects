package model.adt;

import util.AddressBuilder;

import java.util.ArrayList;
import java.util.List;

public class TSem implements IToySem{
    IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> tSem;
    private AddressBuilder addressBuilder = new AddressBuilder();

    public TSem(){
        this.tSem = new MyDict<>();
    }

    @Override
    public void setTSemaphore(IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> semaphore) {
        this.tSem = semaphore;
    }

    @Override
    public void put(Integer foundIndex, Triplet<Integer, ArrayList<Integer>, Integer> integerListPair) {
        tSem.add(foundIndex, integerListPair);
    }

    @Override
    public Integer getTSemAddress() {
        return addressBuilder.getAddress();
    }

    @Override
    public IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> getSemaphore() {
        return tSem;
    }
}
