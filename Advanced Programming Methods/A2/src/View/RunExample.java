package View;

import Controller.Controller;

public class RunExample extends Command{
    private Controller ctr;
    public RunExample(String key, String desceiption, Controller ctr) {
        super(key, desceiption);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try{
            ctr.allStep();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
