package Controller;

import Exceptions.ExecutionExceptions;
import Model.PrgState;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.RefValue;
import Repository.IRepo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            prg.getHeapTable().setContent(this.unsafeGarbageCollector(this.getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeapTable().getContent().values()), prg.getHeapTable().getContent()));
            this.repository.logPrgStateExec();
        }
    }

    private Map<Integer, IValue> unsafeGarbageCollector(List<Integer> used_addresses, Map<Integer, IValue> heap){
        return heap.entrySet().stream().filter((e)->
                {return used_addresses.contains(e.getKey());})
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTblValues, Collection<IValue> heapTblValues){
        List<Integer> symTbl_addresses = (List)symTblValues.stream().filter((v)-> {
            return v instanceof RefValue;
        }).map((v) ->{
            RefValue v1 = (RefValue) v;
            return v1.getAddress();
        }).collect(Collectors.toList());

        List<Integer> heapTbl_addresses = (List)heapTblValues.stream().filter((v) ->{
            return v instanceof RefValue;
        }).map((val) ->{
            RefValue val1 = (RefValue) val;
            return val1.getAddress();
        }).collect(Collectors.toList());
        symTbl_addresses.addAll(heapTbl_addresses);
        return symTbl_addresses;
    }
}
