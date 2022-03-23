package Repository;

import Model.PrgState;
import Model.adt.IList;
import Model.adt.MyList;

import java.util.List;

public interface IRepo {
    //PrgState getCrtPrg();
    void addPrg(PrgState newprg);
    void logPrgStateExec(PrgState prgState) throws Exception;
    IList<PrgState> getPrgList();
    void setPrgList(IList<PrgState> prgList);
}
