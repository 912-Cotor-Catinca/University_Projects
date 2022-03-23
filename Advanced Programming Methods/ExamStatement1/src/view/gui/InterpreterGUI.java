package view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.gui.list.ListController;
import view.gui.program.ProgramController;


public class InterpreterGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage firstStage) throws Exception {
        FXMLLoader listLoader = new FXMLLoader();
        listLoader.setLocation(getClass().getResource("list/list.fxml"));
        Parent listRoot = listLoader.load();
        ListController listController = listLoader.getController();
        firstStage.setTitle("Select a statement");
        Scene listScene = new Scene(listRoot);
        firstStage.setScene(listScene);

        FXMLLoader programLoader = new FXMLLoader();
        programLoader.setLocation(getClass().getResource("program/program.fxml"));
        Parent programRoot = programLoader.load();
        ProgramController programController = programLoader.getController();

        listController.setProgramController(programController);
        Stage secondStage = new Stage();
        secondStage.setTitle("Interpreter");
        Scene programScene = new Scene(programRoot);
        secondStage.setScene(programScene);

        secondStage.show();
        firstStage.show();
    }

}
