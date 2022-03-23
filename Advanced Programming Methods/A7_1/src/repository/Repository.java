package repository;

import exceptions.MyException;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository {
    List<PrgState> prgStates;
    private final String filePath;

    public Repository(String filePath) {
        this.prgStates = new LinkedList<>();
        this.filePath = filePath;
    }

    @Override
    public void add(PrgState state) {
        prgStates.add(state);
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException, IOException {
        System.out.println(state);
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        logFile.println(state);
        logFile.close();
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgStates;
    }

    @Override
    public void setPrgList(List<PrgState> prgStates) {
        this.prgStates = prgStates;
    }
}
