package View;

public class ExitCommand extends View.Command {
    public ExitCommand(String key, String desceiption) {
        super(key, desceiption);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
