package model.adt;

import java.util.ArrayList;
import java.util.List;

public interface IToySem {
    void setTSemaphore(IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> semaphore);
    void put(Integer foundIndex, Triplet<Integer, ArrayList<Integer>, Integer> integerListPair);
    Integer getTSemAddress();
    IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> getSemaphore();
}
