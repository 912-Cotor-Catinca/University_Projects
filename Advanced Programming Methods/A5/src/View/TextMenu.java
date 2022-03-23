package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, View.Command> commands;
    public TextMenu(){
        this.commands = new HashMap<>();
    }

    public void addCommand(View.Command c){
        commands.put(c.getKey(), c);
    }
    private void printMenu(){
        for(View.Command c : commands.values()){
            String line = String.format("%4s: %s", c.getKey(), c.getDesceiption());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key = scanner.nextLine();
            View.Command com = this.commands.get(key);
            if(com == null){
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
        }
    }
}
