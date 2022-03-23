package Controller;

import Farm.Animals;
import Repository.Repository;

public class Controller {
    private Repository repo;
    public Controller(Repository r){
        this.repo = r;
    }

    public void addAnimal(Animals a) throws Exception {
        this.repo.addAnimal(a);
    }

    public void deleteAnimal(String name) throws Exception {
        this.repo.deleteAnimal(name);
    }

    public Animals[] filterByWeight(){
        Animals[] a = this.repo.getAll();
        Animals[] result = new Animals[this.repo.getLength()];
        int i;
        int k = 0;
        for(i = 0; i < this.repo.getLength(); ++i)
        {
            if(a[i].over3kg())
            {
                result[k] = a[i];
                k++;
            }
        }
        Animals[] finalResult = new Animals[k];
        System.arraycopy(result, 0, finalResult, 0, k);
        return finalResult;
    }

    public Animals[] getAll(){
        Animals[] all_animals = new Animals[this.repo.getLength()];
        System.arraycopy(this.repo.getAll(), 0, all_animals, 0, this.repo.getLength());
        return all_animals;
    }
}
