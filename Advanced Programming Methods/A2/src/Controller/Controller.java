package Controller;

import Exceptions.ExecutionExceptions;
import Model.PrgState;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Repository.IRepo;

public class Controller {
    private IRepo repository;
    public Controller(IRepo r){
        this.repository = r;
    }

    public void addPrg(PrgState newprg){
        this.repository.addPrg(newprg);
    }

    public PrgState oneStep(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStack();
        if(stack.isEmpty())
            throw new ExecutionExceptions("Program state stack is empty");
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws Exception {
        PrgState prg = this.repository.getCrtPrg();
        this.repository.logPrgStateExec();
        while(!prg.getStack().isEmpty())
        {
            oneStep(prg);
            this.repository.logPrgStateExec();
        }
    }
}
