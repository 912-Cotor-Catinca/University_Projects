package Controller;

import Exceptions.ExecutionExceptions;
import Model.PrgState;
import Model.adt.IList;
import Model.adt.IStack;
import Model.adt.MyList;
import Model.stmt.IStmt;
import Model.type.RefType;
import Model.value.IValue;
import Model.value.RefValue;
import Repository.IRepo;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepo repository;
    private ExecutorService executor;

    public Controller(IRepo r){
        this.repository = r;
    }

    public void addPrg(PrgState newprg){
        this.repository.addPrg(newprg);
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService ex){
        this.executor = ex;
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotComplete)
                .collect(Collectors.toList());
    }

    void oneStepForAllPrg(List<PrgState> prgStateList) throws Exception {
//        prgStateList.forEach(prg -> {
//            try {
//                this.repository.logPrgStateExec(prg);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        //prepare the list of callables
        List<Callable<PrgState>> callList = prgStateList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        //start the execution of the callables
        // it returns the list of new created PrgStates (namely threads)
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future ->{
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException("future.get() error" + e.getMessage());
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        //add the new created threads to the list of existing threads
        prgStateList.addAll(newPrgList);
        for(PrgState prg : prgStateList)
        {
            repository.logPrgStateExec(prg);
        }
        //Save the current programs in the repository
        IList<PrgState> list = new MyList<>(prgStateList);
        this.repository.setPrgList(list);
    }

    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        IList<PrgState> list = repository.getPrgList();
        List<PrgState> prgStateList = removeCompletedPrg(list.getAll());
        while(prgStateList.size() > 0){
            for(PrgState prg : prgStateList){
                prg.getHeapTable().setContent(safeGarbageCollector(getAddrFromTable(prg.getSymTable().getContent().values()), prg.getHeapTable().getContent()));
            }
            oneStepForAllPrg(prgStateList);
            list = repository.getPrgList();
            prgStateList = removeCompletedPrg(list.getAll());
        }
        executor.shutdownNow();
        IList<PrgState> copyPrgState = new MyList<>(prgStateList);
        this.repository.setPrgList(copyPrgState);
    }


    List<Integer> getAddrFromTable(Collection<IValue> tableValue){
        return tableValue.stream()
                .filter(v -> v.getType() instanceof RefType)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
    Map<Integer, IValue> safeGarbageCollector(List<Integer> symTbl, Map<Integer, IValue> heap){
        List<Integer> heapadrr = getAddrFromTable(heap.values());
        return heap.entrySet().stream()
                .filter(e ->symTbl.contains(e.getKey()) || heapadrr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
