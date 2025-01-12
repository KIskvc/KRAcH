package at.ac.fhcampuswien.blackjack.gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SceneManager.getInstance().setPrimaryStage(primaryStage, "Welcome to Blackjack!");
        SceneManager.getInstance().switchScene("game-view.fxml");
       // SceneManager.getInstance().switchScene("loser-view.fxml");
    }

}
