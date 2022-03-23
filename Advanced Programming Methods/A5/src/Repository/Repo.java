package Repository;

import Model.PrgState;
import Model.adt.IList;
import Model.adt.MyList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Repo implements Repository.IRepo {
    private IList<PrgState> myPrgStates;
    private final String logFilePath;

    public Repo(String logFIle){
        this.logFilePath = logFIle;
        this.myPrgStates = new MyList<PrgState>();
    }

    @Override
    public void addPrg(PrgState newprg) {
        this.myPrgStates.add(newprg);
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws Exception {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            logFile.println(prgState.toString());
            logFile.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public IList<PrgState> getPrgList() {
        return this.myPrgStates;
    }

    @Override
    public void setPrgList(IList<PrgState> prgList) {
        this.myPrgStates = prgList;
    }
}
