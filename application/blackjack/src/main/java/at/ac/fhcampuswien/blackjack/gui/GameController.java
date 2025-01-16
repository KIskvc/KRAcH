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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
    private Button stand;
    @FXML
    private Button split;
    @FXML
    private Button doppelt;

    private ArrayList<Player> player = new ArrayList<>();
    private static final int STARTBALANCE = 1000;
    private Player playerCurrent;
    private Game game;

    @FXML
    public void initialize() throws InterruptedException {
        Dealer dealer = new Dealer("Mr.MakeYouBroke");
        game = new Game(player, dealer);
        Player player1 = new Player("Rana", STARTBALANCE );
        Player player2 = new Player("Kenan", STARTBALANCE );
        Player player3 = new Player("Harun", STARTBALANCE );
        player.add(player1);
        player.add(player2);
        player.add(player3);

        hit.setDisable(true);
        stand.setDisable(true);
        split.setDisable(true);
        doppelt.setDisable(true);

        playerCurrent = null;
        statusTextField.setText("Click Play to start the Game!");
        //statusTextField.setText("Drücke 'Play', um das Spiel zu starten!");
        //initGame();
        //playerCurrent = player.getFirst();
        //placeBetText.setText(playerCurrent.getName() + ", place your bet!");
        placeBetBox.setVisible(false);
    }

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
                //statusTextField.setText(playerCurrent.getName() + "'s aktueller Punktestand: " + handValue);
                statusTextField.setText(playerCurrent.getName() + "'s current Score: " + handValue);
            } else if (handValue == 21) {
                //statusTextField.setText(playerCurrent.getName() + " hat Blackjack erreicht!");
                statusTextField.setText(playerCurrent.getName() + "'s got 21!");
            } else {
                //statusTextField.setText(playerCurrent.getName() + " ist über 21! Bust!");
                statusTextField.setText(playerCurrent.getName() + " is bust!");
            }
            if (handValue >= 21) {
                hit.setDisable(true);
            } else {
                hit.setDisable(false);
            }
        } else {
            //statusTextField.setText("Kein Spieler aktiv. Bitte starte das Spiel.");
            statusTextField.setText("No active player. Please start the game.");
        }
    }

    @FXML
    public void initGame() throws InterruptedException {
        playButton.setVisible(false);

        game.initializeGame();
        playerCurrent = player.get(0);
        //statusTextField.setText(playerCurrent.getName() + " ist an der Reihe.");
        statusTextField.setText(playerCurrent.getName() + "'s turn!");

        placeBetBox.setVisible(true);
        placeBetText.setText(playerCurrent.getName() + ", place your bet!");

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
    public void handleBet() {
        String betText = betTextField.getText();
        int bet;

        if(betText.isEmpty()) {
            errLabel.setText("Please place a bet");
            return;
        }

        try {
            bet = Integer.parseInt(betText);  // Text in Zahl konvertieren
        } catch (NumberFormatException e) {
            errLabel.setText("Invalid! Please enter a number!");
            return;
        }

        if(bet <= 0) {
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
        if(currentIndex < player.size() - 1) {
            setNextPlayer();
            placeBetText.setText(playerCurrent.getName() + ", place your bet!");
        } else {
            placeBetBox.setVisible(false);
            playerCurrent = player.getFirst();

            hit.setDisable(false);
            stand.setDisable(false);
            split.setDisable(false);
            doppelt.setDisable(false);
        }
    }

    public void handleSubmitBet (){
        handleBet();
    }
    // Code block for placing bet

    @FXML
    public void doubleDown() {
        if (playerCurrent != null) {
            try {
                if (playerCurrent.getCurrentBet() == 0) {
                    throw new IllegalStateException(playerCurrent.getName() + " has not placed a bet yet!");
                }

                int doubledBet = playerCurrent.getCurrentBet() * 2;
                if (doubledBet > playerCurrent.getBalance()) {
                    errLabelDouble.setText(playerCurrent.getName() + " doesn't have enough balance to double!");
                    return;
                }

                playerCurrent.doubleBet(game.getDeck(), game);
                hit();

                errLabelDouble.setText(playerCurrent.getName() + "'s bet has doubled! Current bet: " + playerCurrent.getCurrentBet());

                setNextPlayer();

            } catch (IllegalStateException e) {
                errLabelDouble.setText("Error: " + e.getMessage());
            }
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