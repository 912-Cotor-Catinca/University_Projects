package Repository;

import Farm.Animals;
import Farm.Birds;
import Farm.Cows;
import Farm.Pigs;

import java.util.Objects;

public class Repository implements IRepository{
    private Animals[] a;
    private int length;

    public Repository(){
        this.a = new Animals[100];
        this.length = 0;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLength(){
        return this.length;
    }

    @Override
    public void addAnimal(Animals animal) throws Exception{
        if(checkExistance(animal.getName()))
            throw new Exception("Already exists");

        this.a[this.length] = animal;
        this.length++;
     }

    @Override
    public Animals[] getAll() {
        return this.a;
    }

    @Override
    public void deleteAnimal(String name) throws Exception {
        if(this.length < 1)
            throw  new Exception("The list is empty!");

        boolean found = false;
        int pos = 0;
        while(pos < this.length && !found){
            if(Objects.equals(this.a[pos].getName(), name))
                found = true;
            else
                pos++;
        }
        if(found){
            this.a[pos] = this.a[this.length-1];
            this.length--;
        }
        else{
            throw new Exception("The animal does not exist in the list!");
        }

     }
     @Override
     public boolean checkExistance(String name){
         for (int i = 0; i < this.length; ++i)
             if(Objects.equals(this.a[i].getName(), name))
                 return true;
         return false;
     }

     public void init() throws Exception {
        addAnimal(new Birds("Pigeon", 2));
        addAnimal(new Birds("Dove", 1));
        addAnimal(new Birds("Chicken", 4));
        addAnimal(new Birds("Goose", 5));
        addAnimal(new Pigs("Sandu", 8));
        addAnimal(new Pigs("Relu", 9));
        addAnimal(new Pigs("Piggy", 7));
        addAnimal(new Cows("Muu ", 10));
        addAnimal(new Cows("Milka", 15));
     }
}
