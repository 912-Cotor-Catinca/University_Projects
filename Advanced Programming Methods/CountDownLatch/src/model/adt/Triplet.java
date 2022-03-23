package model.adt;

public class Triplet <T1, T2, T3>{
    T1 first;
    T2 second;
    T3 third;
    public Triplet(T1 first,T2 second,T3 third){
        this.first = first;
        this.third = third;
        this.second = second;
    }

    public T1 getFirst(){return this.first;}

    public T2 getSecond(){return this.second;}

    public T3 getThird(){return this.third;}
}
