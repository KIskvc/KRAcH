package at.ac.fhcampuswien.blackjack.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {

    @FXML
    public void leaveGame() {
        Platform.exit();
    }

    @FXML
    public void startGame() {
        SceneManager.getInstance().switchScene("game-view-cards.fxml");
    }

}
