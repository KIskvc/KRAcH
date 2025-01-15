package at.ac.fhcampuswien.blackjack.gui;

import at.ac.fhcampuswien.blackjack.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.awt.*;

public class GameController {

    @FXML
    private HBox firstHand;
    @FXML
    private HBox secondHand;
    @FXML
    private HBox thirdHand;
    @FXML
    private HBox dealerHand;
    @FXML
    private Button playButton;
    @FXML
    private TextField betTextField;
    @FXML
    private Label errLabel;
    @FXML
    private VBox placeBetBox;
    @FXML
    private Text placeBetText;
    @FXML
    private Label errLabelDouble;
    @FXML
    private TextField statusTextField;
    @FXML
    private Button hit;
    @FXML
    private TextField player1;
    @FXML
    private TextField player2;
    @FXML
    private TextField player3;
    @FXML
    private Button submitbtn;
    @FXML
    private Label NameErrorLbl;

    private ArrayList<Player> player = new ArrayList<>();
    private static final int STARTBALANCE = 1000;
    private Player playerCurrent;
    private Game game;

    @FXML
    public void initialize() throws InterruptedException {
        Dealer dealer = new Dealer("Mr.MakeYouBroke");
        game = new Game(player, dealer);
        //Player player1 = new Player("Rana", STARTBALANCE );
        //Player player2 = new Player("Kenan", STARTBALANCE );
        //Player player3 = new Player("Harun", STARTBALANCE );
        //player.add(player1);
        //player.add(player2);
        //player.add(player3);

        hit.setDisable(true);
        //playerCurrent = null;
        statusTextField.setText("Drücke 'Play', um das Spiel zu starten!");
        //initGame();
       // playerCurrent = player.getFirst();
       // placeBetText.setText(playerCurrent.getName() + ", place your bet!");
       // placeBetBox.setVisible(false);
    }

    public void handleSubmitBtn(ActionEvent actionEvent) {

        // Wenn keine Namen at all eingegeben wurden nach Submit
        if (player1.getText().isEmpty() && player2.getText().isEmpty() && player3.getText().isEmpty()) {
            NameErrorLbl.setText("");
            NameErrorLbl.setText("Error! At least one player (Player 1) is required.");
            return;
        }

        // Wenn Player 1 empty ist nach Submit
        // (notwendig um fortzusetzen mit weiterer Namen Eingabe)
        if (player1.getText().isEmpty()) {
            if (player2.getText().matches("[a-zA-Z]+")&& player3.getText().matches("[a-zA-Z]+")) {
                NameErrorLbl.setText("");
                NameErrorLbl.setText("Error! Please enter name(s). Start with Player 1.");
                return;
            }
        }

        //Bedingung, wenn nur 1 Spieler spielt und die Namenfelder "korrekt benutzt" wurden
        //(Nur Buchstaben zulässig)
        if (player1.getText().matches("[a-zA-Z]+")) { // Prüft, ob nur Buchstaben im Textfeld stehen
            if (player2.getText().isEmpty() && player3.getText().isEmpty()) { // Prüft, ob die anderen Felder leer sind
                SceneManager.getInstance().switchScene("game-view.fxml");
            }
        }

        if (player1.getText().matches("[a-zA-Z]+") && player2.getText().matches("[a-zA-Z]+")) { // Prüft, ob nur Buchstaben im Textfeld stehen
            if (player3.getText().isEmpty()) { // Prüft, ob Player 3 TextField leer ist
                SceneManager.getInstance().switchScene("game-view.fxml");
            }
        }

        if (player1.getText().matches("[a-zA-Z]+") && player2.getText().matches("[a-zA-Z]+")
                && player3.getText().matches("[a-zA-Z]+")) { // Prüft, ob nur Buchstaben im Textfeld stehen
            SceneManager.getInstance().switchScene("game-view.fxml");
        }

        if(player2.getText().matches("[a-zA-Z]+")&&player3.getText().matches("[a-zA-Z]+")) {
            if(player1.getText().isEmpty()){
                NameErrorLbl.setText("");
                NameErrorLbl.setText("Error! Please enter name(s). Start with Player 1.");
            }
        }

        if(player1.getText().matches("[a-zA-Z]+")&&player3.getText().matches("[a-zA-Z]+")) {
            if(player2.getText().isEmpty()){
                NameErrorLbl.setText("");
                NameErrorLbl.setText("Error! Please enter name at Player 2.");
            }
        }

        //Wenn nur Player 3
        if(player3.getText().matches("[a-zA-Z]+")) {
            if(player1.getText().isEmpty()&&player2.getText().isEmpty()){
                NameErrorLbl.setText("");
                NameErrorLbl.setText("Error! Please enter name(s). Start with Player 1.");
            }
        }

        // Wenn nur Zahlen eingegeben werden statt Buchstaben
        if (player1.getText().matches("\\d+")||player2.getText().matches("\\d+")||player3.getText().matches("\\d+")) {
            NameErrorLbl.setText("");
            NameErrorLbl.setText("Error! Numbers are not allowed. Please enter a name.");
        }

        if (player1.getText().matches("[a-zA-Z]+")) {
            player.add(new Player(player1.getText(),STARTBALANCE)); // Player 1 hinzufügen
            if (player2.getText().matches("[a-zA-Z]+")) {
                player.add(new Player(player2.getText(),STARTBALANCE)); // Player 2 hinzufügen
                if (player3.getText().matches("[a-zA-Z]+")) {
                    player.add(new Player(player3.getText(),STARTBALANCE)); // Player 3 hinzufügen
                }
            }
            // Szene wechseln, nachdem Spieler hinzugefügt wurden
            SceneManager.getInstance().switchScene("game-view.fxml");
        }


    }

    //Haruns Code
    /* //---Kenans---
    //Change currentPlayer to next Player.
    public void setNextPlayer() {
        try {
            if(playerCurrent == null) {
                playerCurrent = player.getFirst();

            } else {
                int indexOfCurrentPlayer = player.indexOf(playerCurrent);
                int indexOfNewPlayer = indexOfCurrentPlayer + 1;
                if(indexOfNewPlayer >= player.size()-1) {
                    indexOfNewPlayer = 0;
                }
                playerCurrent = player.get(indexOfNewPlayer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    //---Haruns----
    //Change currentPlayer to next Player.
    public void setNextPlayer() {
        try {
            if (playerCurrent == null) {
                playerCurrent = player.get(0);
            } else {
                int indexOfCurrentPlayer = player.indexOf(playerCurrent);
                if (indexOfCurrentPlayer < player.size() - 1) {
                    playerCurrent = player.get(indexOfCurrentPlayer + 1);
                } else {
                    playerCurrent = null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Wechseln des Spielers: " + e.getMessage());
        }
    }

    @FXML
    public void hit() {
        if (playerCurrent != null) {
            Card drawnCard = playerCurrent.hit(game.getDeck());

            String path = drawnCard.getImage();
            Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
            ImageView newCard = new ImageView();
            newCard.setFitWidth(50);
            newCard.setPickOnBounds(true);
            newCard.setPreserveRatio(true);
            newCard.setImage(newCardImage);

            HBox currentHand;
            int currentIndex = player.indexOf(playerCurrent);
            if (currentIndex == 0) {
                currentHand = firstHand; // Rana
            } else if (currentIndex == 1) {
                currentHand = secondHand; // Kenan
            } else {
                currentHand = thirdHand; // Harun
            }
            currentHand.getChildren().add(newCard);
            int handValue = playerCurrent.getHand().getCurrentScore();
            if (handValue < 21) {
                statusTextField.setText(playerCurrent.getName() + "'s aktueller Punktestand: " + handValue);
            } else if (handValue == 21) {
                statusTextField.setText(playerCurrent.getName() + " hat Blackjack erreicht!");
            } else {
                statusTextField.setText(playerCurrent.getName() + " ist über 21! Bust!");
            }
            if (handValue >= 21) {
                hit.setDisable(true);
            } else {
                hit.setDisable(false);
            }
        } else {
            statusTextField.setText("Kein Spieler aktiv. Bitte starte das Spiel.");
        }
    }

    @FXML
    public void initGame() throws InterruptedException {
        playButton.setVisible(false);
        hit.setDisable(false);
        game.initializeGame();
        playerCurrent = player.get(0);
        statusTextField.setText(playerCurrent.getName() + " ist an der Reihe.");

        placeBetBox.setVisible(true);

        Deck currentDeck = game.getDeck();
        for(int i = 0; i < 2; i++) {
            int handcounter = 0;
            for(Player player : game.getPlayer()) {
                dealCardToPlayer(player, currentDeck, handcounter);
                game.setDeck(currentDeck);
                handcounter++;
            }
            dealCardToPlayer(game.getDealer(), currentDeck, handcounter);
        }
    }

    public void dealCardToPlayer(BasePlayer player, Deck deck, int handIndex) {
        Hand currentPlayerHand = player.getHand();
        Card currentCard = deck.dealCard();
        currentPlayerHand.addCard(currentCard);
        HBox currentHbox;
        try {
            if(handIndex == 0) {
                currentHbox = firstHand;
            } else if(handIndex == 1) {
                currentHbox = secondHand;
            } else if(handIndex == 2) {
                currentHbox = thirdHand;
            } else {
                currentHbox = dealerHand;
            }

            /*if(player instanceof Dealer && currentPlayerHand.getCards().size() != 1) {
                int score = currentPlayerHand.getCurrentScore();

            }*/

            String path = currentCard.getImage();
            Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
            ImageView newCard = new ImageView();
            newCard.setFitWidth(50);
            newCard.setPickOnBounds(true);
            newCard.setPreserveRatio(true);
            newCard.setImage(newCardImage);
            currentHbox.getChildren().add(newCard);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void revealDealerCard() {

    }

    // Code block for placing bet
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
        } else if (bet > playerCurrent.getBalance()) {
            errLabel.setText("Brokieee! Place a valid bet!");
            return;
        }

        playerCurrent.setCurrentBet(bet);
        playerCurrent.placeBet(bet);

        betTextField.clear();
        errLabel.setText("");

        int currentIndex = player.indexOf(playerCurrent);
        if(currentIndex < player.size()-1){
            setNextPlayer();
            placeBetText.setText(playerCurrent.getName() + ", place your bet!");
        } else {
            placeBetBox.setVisible(false);
        }
    }

    public void handleSubmitBet (ActionEvent actionEvent){
        handleBet(actionEvent);
    }
    // Code block for placing bet

    @FXML
    public void doubleDown() {
        // noch nicht fertig
        try {
            if (playerCurrent.getCurrentBet() == 0) {
                throw new IllegalStateException("Player has not placed a bet!");
            }
            int doubledBet = playerCurrent.getCurrentBet() * 2;

            if (doubledBet > playerCurrent.getBalance()) {
                errLabelDouble.setText(playerCurrent.getName() + " Not enough balance!");
                return;
            } else {
                playerCurrent.doubleBet(game.getDeck(), game);
                //playerCurrent.hit(game.getDeck());

                Deck currentDeck = game.getDeck();
                int handIndex = player.indexOf(playerCurrent);
                dealCardToPlayer(playerCurrent, currentDeck, handIndex);

                errLabelDouble.setText(playerCurrent.getName() + ", Bet has been doubled! " + playerCurrent.getCurrentBet());
            }
            setNextPlayer();

        } catch (IllegalStateException e) {
        errLabelDouble.setText("Error: " + e.getMessage());
        }
    }


    @FXML
    public void handleLeaveButton(ActionEvent event) {
        SceneManager.getInstance().switchScene("main-view.fxml");
    }

    @FXML
    public void handleStandButton(ActionEvent event) {
        if (playerCurrent != null) {
            playerCurrent.stand();
            setNextPlayer();

            if (playerCurrent != null) {
                statusTextField.setText(playerCurrent.getName() + " ist an der Reihe.");
                hit.setDisable(false);
            } else {
                statusTextField.setText("Alle Spieler sind fertig. Dealer ist dran!");
                hit.setDisable(true);
                Dealer dealer = game.getDealer();
                dealer.playTurn(game);
                revealDealerCard();
            }
        } else {
            statusTextField.setText("Kein Spieler aktiv. Bitte starte das Spiel.");
        }
    }
}
