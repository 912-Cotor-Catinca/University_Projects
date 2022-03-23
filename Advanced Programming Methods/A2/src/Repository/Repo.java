package Repository;

import Exceptions.ADTExceptions;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.adt.MyList;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.*;

public class Repo implements IRepo{
    private MyList<PrgState> myPrgStates;
    private final String logFilePath;

    public Repo(String logFIle){
        this.logFilePath = logFIle;
        this.myPrgStates = new MyList<PrgState>();
    }

    @Override
    public PrgState getCrtPrg() {
        int last = this.myPrgStates.size() - 1;
        return this.myPrgStates.get(last);
    }

    @Override
    public void addPrg(PrgState newprg) {
        this.myPrgStates.add(newprg);
    }

    @Override
    public void logPrgStateExec() throws Exception {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            logFile.println(this.getCrtPrg().toString());
            logFile.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
