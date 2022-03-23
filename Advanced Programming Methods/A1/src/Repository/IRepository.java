package Repository;

import Farm.Animals;

public interface IRepository {
    public void addAnimal(Animals a) throws Exception;
    public void deleteAnimal(String name) throws Exception;
    public boolean checkExistance(String name);
    public Animals[] getAll();
}
