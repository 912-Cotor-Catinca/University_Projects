package model.adt;

import java.util.List;

public interface ISemaphore {
    void setSemaphore(IDict<Integer, Pair<Integer, List<Integer>>> semaphore);
    void put(Integer foundIndex, Pair<Integer, List<Integer>> integerListPair);
    Integer getSemAddress();
    IDict<Integer, Pair<Integer, List<Integer>>> getSemaphore();
}
