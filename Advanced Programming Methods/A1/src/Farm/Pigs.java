package Farm;

public class Pigs implements Animals{
    private String name;
    private float weight;
    public Pigs(String name, float weight){
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
        return this.name + " " + this.weight +"kg";
    }
}
