package model.adt;

import util.AddressBuilder;

import java.util.List;

public class Barrier implements IBarrier{
    IDict<Integer, Pair<Integer, List<Integer>>> barrier;
    private AddressBuilder addressBuilder = new AddressBuilder();
    public Barrier(){
        this.barrier = new MyDict<>();
    }
    @Override
    public void setBarrier(IDict<Integer, Pair<Integer, List<Integer>>> barrier) {
        this.barrier = barrier;
    }

    @Override
    public void put(Integer foundIndex, Pair<Integer, List<Integer>> integerListPair) {
        this.barrier.add(foundIndex, integerListPair);
    }

    @Override
    public Integer getBarrierAddress() {
        return addressBuilder.getAddress();
    }

    @Override
    public IDict<Integer, Pair<Integer, List<Integer>>> getBarrier() {
        return barrier;
    }
}
