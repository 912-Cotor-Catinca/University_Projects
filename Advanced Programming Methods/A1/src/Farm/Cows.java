package Farm;

public class Cows implements Animals{
    private String name;
    private float weight;
    public Cows(String name, float weight){
        this.name = name;
        this.weight = weight;
    }

    @Override
    public boolean over3kg() {
        return this.weight > 3;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }
    @Override
    public String toString(){
        return this.name + " " + this.weight + "kg";
    }
}
