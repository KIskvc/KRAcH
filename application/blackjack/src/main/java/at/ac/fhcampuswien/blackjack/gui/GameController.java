package at.ac.fhcampuswien.blackjack.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.awt.*;

public class GameController {


    //Player-Screen-view Code (not done yet)
    public TextArea player1;
    public TextArea player2;
    public TextArea player3;
    @FXML
    private Button submitbtn;

    public void handleSubmitBtn(ActionEvent actionEvent) {
        player1.getText();
        player2.getText();
        player3.getText();

    }
}
