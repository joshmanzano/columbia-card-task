package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("scene.fxml").openStream());
        Model model = new Model();
        Controller controller = (Controller) fxmlLoader.getController();
        controller.setModel(model);
        model.setController(controller);
        primaryStage.setTitle("Columbia Card Task");
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add
                (getClass().getResource("styles.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
