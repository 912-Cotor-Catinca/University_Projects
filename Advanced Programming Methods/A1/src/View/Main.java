package View;

import Controller.Controller;
import Farm.Animals;
import Farm.Birds;
import Farm.Cows;
import Farm.Pigs;
import Repository.Repository;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Repository repo = new Repository();
        Controller controller = new Controller(repo);
        menu(controller);
    }
    public static void menu(Controller controller) throws Exception{
        boolean ok = true;
        while(ok)
        {
            System.out.println("0. Exit");
            System.out.println("1. Add a bird");
            System.out.println("2. Add a pig");
            System.out.println("3. Add a cow");
            System.out.println("4. Delete");
            System.out.println("5. Filter by weight");
            System.out.println("6. See all animals");
            Scanner ob = new Scanner(System.in);
            int cmd = ob.nextInt();
            switch (cmd)
            {
                case 0:
                    ok = false;
                    break;
                case 1:
                    String name = readName();
                    int weight = readWeight();
                    try {
                        controller.addAnimal(new Birds(name, weight));
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.toString());
                    }
                    break;
                case 2:
                    String name1 = readName();
                    int weight1 = readWeight();
                    try {
                        controller.addAnimal(new Pigs(name1, weight1));
                    }
                    catch (Exception e){
                        System.out.println(e.toString());
                    }
                    break;
                case 3:
                    String name2 = readName();
                    int weight2 = readWeight();
                    try {
                        controller.addAnimal(new Cows(name2, weight2));
                    }
                    catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    break;
                case 4:
                    String name_delete = readName();
                    try {
                        controller.deleteAnimal(name_delete);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Filtered list: ");
                    Animals[] animals = controller.filterByWeight();
                    for(Animals a : animals)
                        System.out.println(a.toString());
                    break;
                case 6:
                    System.out.println("Animal Farm: ");
                    Animals[] all_animals = controller.getAll();
                    for(Animals a : all_animals)
                        System.out.println(a.toString());
            }
        }
    }

    public static String readName(){
        System.out.println("Enter name");
        Scanner ob1 = new Scanner(System.in);
        String name = ob1.nextLine();
        return name;
    }

    public static int readWeight(){
        System.out.println("Enter weight");
        Scanner ob2 = new Scanner(System.in);
        int weight = ob2.nextInt();
        return weight;
    }

}
