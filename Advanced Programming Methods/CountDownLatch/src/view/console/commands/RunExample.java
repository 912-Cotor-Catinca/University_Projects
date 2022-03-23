package view.console.commands;

import Controller.Controller;
import exceptions.MyException;

import java.io.IOException;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        }
        catch (MyException | IOException exception) {
            System.out.printf("The following exception was caught: %s\n", exception.getMessage());
        }
    }
}
