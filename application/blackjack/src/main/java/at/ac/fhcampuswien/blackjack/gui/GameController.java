package at.ac.fhcampuswien.blackjack.gui;

import at.ac.fhcampuswien.blackjack.models.BasePlayer;
import at.ac.fhcampuswien.blackjack.models.Dealer;
import at.ac.fhcampuswien.blackjack.models.Game;
import at.ac.fhcampuswien.blackjack.models.Player;
import javafx.fxml.FXML;

import java.util.ArrayList;

public class GameController {

    ArrayList<Player> player;

    @FXML
    public void initialize() {
        Dealer dealer = new Dealer("Mr.MakeYouBroke");
        Game game = new Game(player, dealer);
    }

}
