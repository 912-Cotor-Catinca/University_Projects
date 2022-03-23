package View;

public abstract class Command {
    private String key, desceiption;

    public Command(String key, String desceiption){
        this.key = key;
        this.desceiption = desceiption;
    }
    public String getKey(){return this.key;}
    public String getDesceiption(){return this.desceiption;}

    public abstract void execute();
}
