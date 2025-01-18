package at.ac.fhcampuswien.blackjack.gui;

import at.ac.fhcampuswien.blackjack.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.util.*;

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
    @FXML
    private HBox firstSplitHandBox;
    @FXML
    private HBox secondSplitHandBox;
    @FXML
    private HBox thirdSplitHandBox;

    private ArrayList<Player> player = new ArrayList<>();
    private static final int STARTBALANCE = 1000;
    private Player playerCurrent;
    private Game game;
    private Map<Player, List<Hand>> playerHands = new HashMap<>();

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

    @FXML
    public void handleSubmitBtn(ActionEvent actionEvent) {
        //eigener Controller?
        //arraylist beibehalten
        //Scene manager adaptieren

        // Wenn keine Namen at all eingegeben wurden nach Submit
        if (player1.getText().isEmpty()) {
            NameErrorLbl.setText("Error! At least one player (Player 1) is required.");
            return;
        }else{
            Player newplayer = new Player(player1.getText(),STARTBALANCE);
            player.add(newplayer);
        }

        if (!player2.getText().isEmpty()) {
            Player newplayer = new Player(player2.getText(),STARTBALANCE);
            player.add(newplayer);
        }

        if (!player3.getText().isEmpty()&&!player2.getText().isEmpty()) {
            Player newplayer = new Player(player3.getText(),STARTBALANCE);
            player.add(newplayer);
        }

       SceneManager.getInstance().switchScene("game-view.fxml");

    }

    @FXML
    public void handleSplitButton() {
        // Überprüfung ob Split möglich is
        if (playerCurrent == null || playerCurrent.getHand().getCards().size() != 2) {
            statusTextField.setText("Split is only possible if you have 2 Cards");
            return;
        }

        Card firstCard = playerCurrent.getHand().getCards().get(0);
        Card secondCard = playerCurrent.getHand().getCards().get(1);

        if (firstCard.getValue() != secondCard.getValue()) {
            statusTextField.setText("Cards have different values! Split is not possible!");
            return;
        }

        Hand currentHand = playerCurrent.getHand();
        Card card1 = currentHand.getCards().get(0);
        Card card2 = currentHand.getCards().get(1);

        Hand hand1 = new Hand();
        Hand hand2 = new Hand();

        hand1.addCard(card1);
        hand2.addCard(card2);

        // Füge die neuen Hands zur Liste hinzu
        List<Hand> hands = playerHands.getOrDefault(playerCurrent, new ArrayList<>());
        hands.add(hand1);
        hands.add(hand2);
        playerHands.put(playerCurrent, hands); //speichern der Hands in der Map

        // neune Karten
        Card newCardHand1 = game.getDeck().dealCard();
        hand1.addCard(newCardHand1);
        Card newCardHand2 = game.getDeck().dealCard();
        hand2.addCard(newCardHand2);

        // updaten der beiden hands
        if (player.indexOf(playerCurrent) == 0) {
            updateHandBox(firstHand,hand1);
            updateHandBox(firstSplitHandBox,hand2);
            firstSplitHandBox.setVisible(true);
        } else if (player.indexOf(playerCurrent) == 1) {
            updateHandBox(secondHand,hand1);
            updateHandBox(secondSplitHandBox,hand2);
            secondSplitHandBox.setVisible(true);
        } else if (player.indexOf(playerCurrent) == 2) {
            updateHandBox(thirdHand,hand1);
            updateHandBox(thirdSplitHandBox,hand2);
            thirdSplitHandBox.setVisible(true);
        }
    }

    public void updateHandBox(HBox handBox, Hand hand){
        handBox.getChildren().clear();
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card card = hand.getCards().get(i);
            String path = card.getImage();
            Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
            ImageView newCard = new ImageView();
            newCard.setFitWidth(50);
            newCard.setPickOnBounds(true);
            newCard.setPreserveRatio(true);
            newCard.setImage(newCardImage);
            handBox.getChildren().add(newCard);
        }
    }


    /* ---Kenans---
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
            throw new RuntimeException("Error occurred with switching player: " + e.getMessage());
        }
    }

    @FXML
    public void hit() {
        if (playerCurrent != null) {
            // Ziehe eine Karte für den aktuellen Spieler
            Card drawnCard = playerCurrent.hit(game.getDeck());

            // Visualisierung der gezogenen Karte
            String path = drawnCard.getImage();
            Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
            ImageView newCard = new ImageView();
            newCard.setFitWidth(50);
            newCard.setPickOnBounds(true);
            newCard.setPreserveRatio(true);
            newCard.setImage(newCardImage);

            // Zeige die Karte im richtigen Handbereich an
            HBox currentHand;
            int currentIndex = player.indexOf(playerCurrent);
            if (currentIndex == 0) {
                currentHand = firstHand;
            } else if (currentIndex == 1) {
                currentHand = secondHand;
            } else {
                currentHand = thirdHand;
            }
            currentHand.getChildren().add(newCard);

            // Überprüfe den Handwert des Spielers
            int handValue = playerCurrent.getHand().getCurrentScore();
            if (handValue < 21) {
                statusTextField.setText(playerCurrent.getName() + "'s current score: " + handValue);
            } else {
                if (handValue == 21) {
                    statusTextField.setText(playerCurrent.getName() + " reached Blackjack!");
                } else {
                    statusTextField.setText(playerCurrent.getName() + " is over 21! Bust!");
                }

                // Wechsle zum nächsten Spieler
                setNextPlayer();

                if (playerCurrent != null) {
                    statusTextField.setText(playerCurrent.getName() + "'s turn. Make your move!");
                    hit.setDisable(false); // Hit-Button für den nächsten Spieler aktivieren
                } else {
                    // Wenn keine Spieler mehr verfügbar sind, startet der Dealer
                    statusTextField.setText("All players are done. Dealer's turn!");
                    hit.setDisable(true);
                    Dealer dealer = game.getDealer();
                    dealer.playTurn(game);
                    revealDealerCard();
                }
            }
        } else {
            statusTextField.setText("No active players. Please start game.");
        }
    }

    @FXML
    public void initGame() throws InterruptedException {
        playButton.setVisible(false);
        //hit.setDisable(false);
        game.initializeGame();
        playerCurrent = player.get(0);
        statusTextField.setText(playerCurrent.getName() + ", it is your turn.");

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