package view.gui.program;

import Controller.Controller;
import com.sun.javafx.image.IntPixelGetter;
import exceptions.MyException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.PrgState;
import model.adt.*;
import model.statements.IStmt;
import model.values.IValue;

import java.util.*;
import java.util.stream.Collectors;

public class ProgramController {
    private Controller controller;

    @FXML
    private TableView<Pair<Integer, IValue>> heapTable;
    @FXML
    private TableView<Pair<Integer, Pair<Integer, List<Integer>>>> barrier;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> barrierIndexCol;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> barrierValCol;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, List<Integer>> barrierList;

    @FXML
    private TableView<Pair<Integer, Integer>> latchTable;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> valueLatch;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> indexColumn;


    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, String> valueColumn;

    @FXML
    private ListView<String> outputList;

    @FXML
    private ListView<String> fileList;

    @FXML
    private ListView<Integer> prgStateList;

    @FXML
    private ListView<String> exeStackList;

    @FXML
    private TableView<Pair<String, IValue>> symTable;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symVariableColumn;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symValueColumn;

    @FXML
    private TableView<Pair<Integer, Integer>> lockTable;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> lockIndexCol;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> lockValCol;
    
    @FXML
    private TableView<Pair<Integer, Triplet<Integer, ArrayList<Integer>, Integer>>> toySemaphore;
    
    @FXML
    private TableColumn<Pair<Integer, Triplet<Integer, ArrayList<Integer>, Integer>>, Integer> indextoySem;
    
    @FXML
    private TableColumn<Pair<Integer, Triplet<Integer, ArrayList<Integer>, Integer>>, Integer> valueToyS;
    
    @FXML
    private TableColumn<Pair<Integer, Triplet<Integer, ArrayList<Integer>, Integer>>, ArrayList<Integer>> arrayListTableColumn;

    @FXML
    private TableView<Pair<Integer, Pair<Integer, List<Integer>>>> semaphore;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> valueSemCol;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> indexColumnSem;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, List<Integer>> listSemaphore;

    @FXML
    private TextField numberOfPrgStates;

    @FXML
    private Button oneStepButton;

    @FXML
    public void initialize() {
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getFirst()).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSecond().toString()));
        symVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getFirst()));
        symValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSecond().toString()));
        indexColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getFirst()).asObject());
        valueLatch.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getSecond()).asObject());
        barrierIndexCol.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getFirst()).asObject());
        barrierValCol.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getSecond().getFirst()).asObject());
        barrierList.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getValue().getValue()));

        lockIndexCol.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getFirst()).asObject());
        lockValCol.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getSecond()).asObject());
        
        indextoySem.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getFirst()).asObject());
        valueToyS.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getSecond().getFirst()).asObject());
        arrayListTableColumn.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getSecond().getSecond()));

        indexColumnSem.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getFirst()).asObject());
        valueSemCol.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getValue().getFirst()).asObject());
        listSemaphore.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getValue().getValue()));

        oneStepButton.setOnAction(actionEvent -> {
            if (controller == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected!", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if (getCurrentProgramState() == null || getCurrentProgramState().getExeStack().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute!", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            try {
                controller.oneStepAll();
                populate();
            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        prgStateList.setOnMouseClicked(event -> populate());
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    private PrgState getCurrentProgramState(){
        if (controller.getPrgStates().size() == 0)
            return null;
        int currentId = prgStateList.getSelectionModel().getSelectedIndex();
        if (currentId == -1)
            return controller.getPrgStates().get(0);
        return controller.getPrgStates().get(currentId);
    }

    private void populate() {
        populateHeap();
        populateProgramStateIdentifiers();
        populateFileTable();
        populateOutput();
        populateSymbolTable();
        populateExecutionStack();
        populateLatchTable();
        populateBarrierTable();
        populateLockTable();
        populateToySem();
        populateSem();
    }

    private void populateSem() {
        IDict<Integer, Pair<Integer, List<Integer>>> semTable;
        if(controller.getPrgStates().size() > 0)
            semTable = controller.getPrgStates().get(0).getSemaphore().getSemaphore();
        else
            semTable = new MyDict<>();

        List<Pair<Integer, Pair<Integer, List<Integer>>>> semList = new ArrayList<>();
        for(Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : semTable.getContent().entrySet())
            semList.add(new Pair<>(entry.getKey(), entry.getValue()));
        semaphore.setItems(FXCollections.observableList(semList));
        semaphore.refresh();
    }

    private void populateToySem() {
        IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> TsemTable;
        if(controller.getPrgStates().size() > 0)
            TsemTable = controller.getPrgStates().get(0).getToySem().getSemaphore();
        else
            TsemTable = new MyDict<>();

        List<Pair<Integer, Triplet<Integer, ArrayList<Integer>, Integer>>> TsemList = new ArrayList<>();
        for(Map.Entry<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> entry : TsemTable.getContent().entrySet())
            TsemList.add(new Pair<>(entry.getKey(), entry.getValue()));
        toySemaphore.setItems(FXCollections.observableList(TsemList));
        toySemaphore.refresh();
    }

    private void populateLockTable() {
        ILockTable<Integer> lock;
        if(controller.getPrgStates().size() > 0)
            lock = controller.getPrgStates().get(0).getLockTable();
        else
            lock = new LockTable<>();
        List<Pair<Integer, Integer>> lockList = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry:lock.getContent().entrySet())
            lockList.add(new Pair<>(entry.getKey(), entry.getValue()));
        lockTable.setItems(FXCollections.observableList(lockList));
        lockTable.refresh();
    }

    private void populateBarrierTable() {
        IDict<Integer, Pair<Integer, List<Integer>>> barrierTable;
        if(controller.getPrgStates().size() > 0)
            barrierTable = controller.getPrgStates().get(0).getBarrier().getBarrier();
        else
            barrierTable = new MyDict<>();
        List<Pair<Integer, Pair<Integer, List<Integer>>>> barrierL = new ArrayList<>();
        for(Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : barrierTable.getContent().entrySet())
            barrierL.add(new Pair<>(entry.getKey(), entry.getValue()));
        barrier.setItems(FXCollections.observableList(barrierL));
        barrier.refresh();
    }

    private void populateLatchTable() {
        ILatchTable countLatchTable;
        if(controller.getPrgStates().size() > 0)
            countLatchTable = controller.getPrgStates().get(0).getLatchTable();
        else
            countLatchTable = new LatchTable();

        List<Pair<Integer, Integer>> latchList = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : countLatchTable.getContent().getContent().entrySet())
            latchList.add(new Pair<>(entry.getKey(), entry.getValue()));
        latchTable.setItems(FXCollections.observableList(latchList));
        latchTable.refresh();
    }

    private void populateHeap() {
        IHeap<IValue> heap;
        if (controller.getPrgStates().size() > 0)
            heap = controller.getPrgStates().get(0).getHeapTable();
        else
            heap = new MyHeap<>();
        List<Pair<Integer, IValue>> heapTableList = new ArrayList<>();
        for (Map.Entry<Integer, IValue> entry : heap.getContent().entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTable.setItems(FXCollections.observableList(heapTableList));
        heapTable.refresh();
    }

    private void populateProgramStateIdentifiers() {
        List<PrgState> programStates = controller.getPrgStates();
        List<Integer> idList = programStates.stream().map(ps -> ps.id).collect(Collectors.toList());
        prgStateList.setItems(FXCollections.observableList(idList));
        numberOfPrgStates.setText(String.valueOf(programStates.size()));
    }

    private void populateFileTable() {
        List<String> list = new LinkedList<>();
        if (controller.getPrgStates().size() > 0)
            controller.getPrgStates().get(0).getFileTable().getContent().forEach((key, value) -> list.add(key.getVal()));
        fileList.setItems(FXCollections.observableList(list));
    }

    private void populateOutput() {
        IList<String> output;
        output = controller.getOutput();
        if (output != null) {
            outputList.setItems(FXCollections.observableList(output.getList()));
            outputList.refresh();
        }
    }

    private void populateSymbolTable() {
        PrgState state = getCurrentProgramState();
        List<Pair<String, IValue>> symbolTableList = new ArrayList<>();
        if (state != null)
            for (Map.Entry<String, IValue> entry : state.getSymTable().getContent().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        symTable.setItems(FXCollections.observableList(symbolTableList));
        symTable.refresh();
    }

    private void populateExecutionStack() {
        PrgState state = getCurrentProgramState();
        List<String> exeStackListAsString = new ArrayList<>();
        if (state != null) {
            Stack<IStmt> stack = state.getExeStack().getStack();
            ListIterator<IStmt> stackIterator = stack.listIterator(stack.size());
            while (stackIterator.hasPrevious()) {
                exeStackListAsString.add(stackIterator.previous().toString());
            }
        }
        exeStackList.setItems(FXCollections.observableList(exeStackListAsString));
        exeStackList.refresh();
    }
}
