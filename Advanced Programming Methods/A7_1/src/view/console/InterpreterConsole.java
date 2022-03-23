package view.console;

import controller.Controller;
import model.PrgState;
import model.adt.MyDict;
import model.statements.IStmt;
import repository.IRepository;
import repository.Repository;
import view.console.commands.ExitCommand;
import view.console.commands.RunExample;

import static view.Examples.getExamples;

public class InterpreterConsole {

    public static void main(String[] args) {
        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "Exit"));
        int exampleNr = 1;
        for (IStmt stmt: getExamples()) {
            stmt.typeCheck(new MyDict<>());
            PrgState prgState = new PrgState(stmt);
            IRepository repository = new Repository(String.format("log%s.txt", exampleNr));
            repository.add(prgState);
            Controller controller = new Controller(repository);
            textMenu.addCommand(new RunExample(Integer.toString(exampleNr), stmt.toString(), controller));
            exampleNr++;
        }
        textMenu.show();
    }
}
