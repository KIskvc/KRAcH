package at.ac.fhcampuswien.blackjack.gui;

import at.ac.fhcampuswien.blackjack.models.BasePlayer;
import at.ac.fhcampuswien.blackjack.models.Dealer;
import at.ac.fhcampuswien.blackjack.models.Game;
import at.ac.fhcampuswien.blackjack.models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameController {
    ArrayList<Player> player;
    @FXML
    private TextField betTextField;
    @FXML
    private Label errLabel;
    @FXML
    private VBox placeBetBox;
    @FXML
    private Text placeBetText;

    int currentPlayer = 0;

    @FXML
    public void initialize() {
        Dealer dealer = new Dealer("Mr.MakeYouBroke");
        player = new ArrayList<>();
        Game game = new Game(player, dealer);

        // zum testen
        Player player1 = new Player("Anna", 1000);
        player.add(player1);
        Player player2 = new Player("Rana", 500);
        player.add(player2);
        Player player3 = new Player("Kenan", 300);
        player.add(player3);

        placeBetText.setText(player.getFirst().getName() + ", place your bet!");
        placeBetBox.setVisible(true);
    }

    @FXML
    public void handleBet(ActionEvent event) {
        String betText = betTextField.getText();
        int bet;


        if (betText.isEmpty()) {
            errLabel.setText("Please place a bet");
            return;
        }

        try {
            bet = Integer.parseInt(betText);  // Text in Zahl konvertieren
        } catch (NumberFormatException e) {
            errLabel.setText("Invalid! Please enter a number!");
            return;
        }

        if (bet <= 0) {
            errLabel.setText("Invalid bet! Please enter a positive number!");
            return;
        } else if (bet > player.get(currentPlayer).getBalance()) {
            errLabel.setText("Brokieee! Place a valid bet!");
            return;
        }

        player.get(currentPlayer).setCurrentBet(bet);
        player.get(currentPlayer).placeBet(bet);

        betTextField.clear();
        errLabel.setText("");

        currentPlayer++;

        if (currentPlayer < player.size()) {
            placeBetText.setText(player.get(currentPlayer).getName() + ", place your bet!");
        } else {
            placeBetBox.setVisible(false);
        }
    }


    public void handleSubmitBet (ActionEvent actionEvent){
        handleBet(actionEvent);
    }
}
