package repository;

import exceptions.MyException;
import model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void add(PrgState state);
    void logPrgStateExec(PrgState state) throws MyException, IOException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgStates);
}
