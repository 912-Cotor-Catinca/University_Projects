package view.gui.program;

import Controller.Controller;
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
    private TextField numberOfPrgStates;

    @FXML
    private Button oneStepButton;

    @FXML
    public void initialize() {
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getFirst()).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSecond().toString()));
        symVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getFirst()));
        symValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSecond().toString()));


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
