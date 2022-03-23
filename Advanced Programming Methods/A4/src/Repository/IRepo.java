package Repository;

import Model.PrgState;

public interface IRepo {
    PrgState getCrtPrg();
    void addPrg(PrgState newprg);
    void logPrgStateExec() throws Exception;
}
