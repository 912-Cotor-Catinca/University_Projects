package model.adt;

import java.util.List;

public interface IBarrier {
    void setBarrier(IDict<Integer, Pair<Integer, List<Integer>>> Barrier);
    void put(Integer foundIndex, Pair<Integer, List<Integer>> integerListPair);
    Integer getBarrierAddress();
    IDict<Integer, Pair<Integer, List<Integer>>> getBarrier();
}
