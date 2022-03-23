package model.adt;

import util.AddressBuilder;

import java.util.List;

public class Semaphore implements ISemaphore{
    IDict<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private AddressBuilder addressBuilder = new AddressBuilder();

    public Semaphore(){
        this.semaphoreTable = new MyDict<>();
    }


    @Override
    public void setSemaphore(IDict<Integer, Pair<Integer, List<Integer>>> semaphore) {
        this.semaphoreTable=semaphore;
    }

    @Override
    public void put(Integer foundIndex, Pair<Integer, List<Integer>> integerListPair) {
        semaphoreTable.add(foundIndex,integerListPair);
    }

    @Override
    public Integer getSemAddress() {
        return addressBuilder.getAddress();
    }

    @Override
    public IDict<Integer, Pair<Integer, List<Integer>>> getSemaphore() {
        return semaphoreTable;
    }
}
