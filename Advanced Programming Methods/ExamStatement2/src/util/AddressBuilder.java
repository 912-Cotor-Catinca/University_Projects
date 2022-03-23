package util;

import model.adt.IStack;
import model.adt.MyStack;

public class AddressBuilder {
    private Integer address = 1;
    private static IStack<Integer> freeAddr = new MyStack<>();

    public AddressBuilder(){}

    public Integer getAddress(){
        return freeAddr.isEmpty()? this.address++ : freeAddr.pop();
    }
}
