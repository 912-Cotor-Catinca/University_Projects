package view.gui.list;

import controller.Controller;
import exceptions.MyException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import model.PrgState;
import model.adt.MyDict;
import model.statements.IStmt;
import repository.IRepository;
import repository.Repository;
import view.gui.program.ProgramController;

import static view.Examples.getExamples;

public class ListController {
    private ProgramController programController;

    public void setProgramController(ProgramController programController) {
        this.programController = programController;
    }

    @FXML
    private ListView<IStmt> statements;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        statements.setItems(FXCollections.observableArrayList(getExamples()));
        displayButton.setOnAction(actionEvent -> {
            int index = statements.getSelectionModel().getSelectedIndex();
            if (index < 0)
                return;
            IStmt stmt = getExamples()[index];
            PrgState prgState = new PrgState(stmt);
            IRepository repository = new Repository(String.format("log%s.txt", index+1));
            repository.add(prgState);
            Controller controller = new Controller(repository);
            try {
                stmt.typeCheck(new MyDict<>());
                programController.setController(controller);
            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        });
    }
}
