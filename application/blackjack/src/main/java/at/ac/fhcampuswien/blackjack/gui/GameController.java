package at.ac.fhcampuswien.blackjack.gui;

import at.ac.fhcampuswien.blackjack.models.BasePlayer;
import at.ac.fhcampuswien.blackjack.models.Dealer;
import at.ac.fhcampuswien.blackjack.models.Game;
import at.ac.fhcampuswien.blackjack.models.Player;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;


import java.util.ArrayList;

public class GameController {

    ArrayList<Player> player;

    @FXML
    public void initialize() {
        Dealer dealer = new Dealer("Mr.MakeYouBroke");
        Game game = new Game(player, dealer);
    }

    @FXML
    public void handleLeaveButton(ActionEvent event) {
        SceneManager.getInstance().switchScene("main-view.fxml");
    }

    @FXML
    public void handleStandButton(ActionEvent event) {
        if (player != null && !player.isEmpty()) {
            Player currentPlayer = player.get(0);
            currentPlayer.stand();
            System.out.println(currentPlayer.getName() + " hat gestanden.");
            Dealer dealer = new Dealer("Mr.MakeYouBroke");
            dealer.playTurn(new Game(player, dealer));
        } else {
            System.out.println("Kein Spieler verfügbar.");
        }
    }
}

