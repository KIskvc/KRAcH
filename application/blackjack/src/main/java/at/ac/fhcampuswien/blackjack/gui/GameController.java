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
    private Button split;
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

        //hit.setDisable(true);
        //playerCurrent = null;
        //statusTextField.setText("Drücke 'Play', um das Spiel zu starten!");
        //initGame();
        //playerCurrent = player.getFirst();
        //placeBetText.setText(playerCurrent.getName() + ", place your bet!");
        //placeBetBox.setVisible(false);
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
/*
    @FXML
    public void handleSplitButton() {
    /*
        if wert der 2 karten gleich, dann split möglich
            split um die hand zu teilen
            anschließend werden für jede hand 1 karte gezogen (hit), hit für beide hände
            hit also aufrufen für beide hände

        wenn nicht, dann disable button




        Hand currentHand = playerCurrent.getHand();
        if (currentHand.getCards().size() == 2 && currentHand.getCards().get(0).getValue() == currentHand.getCards().get(1).getValue()) {
            split.setDisable(false);
        }else {
            split.setDisable(true);
        }

        if (playerCurrent != null) {

            //Überprüfen, ob Split möglich ist
            if (currentHand.getCards().size() == 2 && currentHand.getCards().get(0).getValue() == currentHand.getCards().get(1).getValue()) {
                currentHand.split(game.getDeck().dealCard()); //Split durchführen

                // UI für gesplittete Hände aktualisieren
                HBox playersFirstHandBox = new HBox();
                HBox playersSecondHandBox = new HBox();

                // Erste gesplittete Hand anzeigen
                for (Card card : currentHand.getSplitCards().get(0)) {
                    ImageView cardImageView = createCardImageView(card);
                    playersFirstHandBox.getChildren().add(cardImageView);
                }

                // Zweite gesplittete Hand anzeigen
                for (Card card : currentHand.getSplitCards().get(1)) {
                    ImageView cardImageView = createCardImageView(card);
                    playersSecondHandBox.getChildren().add(cardImageView);
                }
                statusTextField.setText(playerCurrent.getName() + " split cards successfully!");
            }

            }
        }

*/
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
                statusTextField.setText(playerCurrent.getName() + "'s current score: " + handValue);
            } else if (handValue == 21) {
                statusTextField.setText(playerCurrent.getName() + " reached Blackjack!");
            } else {
                statusTextField.setText(playerCurrent.getName() + " is over 21! Bust!");
            }
            if (handValue >= 21) {
                hit.setDisable(true);
            } else {
                hit.setDisable(false);
            }
        } else {
            statusTextField.setText("No active players. Please start game.");
        }
    }

    @FXML
    public void initGame() throws InterruptedException {
        playButton.setVisible(false);
        hit.setDisable(false);
        game.initializeGame();
        playerCurrent = player.get(0);
        statusTextField.setText(playerCurrent.getName() + ", it is your turn.");

        placeBetBox.setVisible(true);

        Deck currentDeck = game.getDeck();
        for (int i = 0; i < 2; i++) {
            int handcounter = 0;
            for (Player player : game.getPlayer()) {
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
            if (handIndex == 0) {
                currentHbox = firstHand;
            } else if (handIndex == 1) {
                currentHbox = secondHand;
            } else if (handIndex == 2) {
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
        if (currentIndex < player.size() - 1) {
            setNextPlayer();
            placeBetText.setText(playerCurrent.getName() + ", place your bet!");
        } else {
            placeBetBox.setVisible(false);
        }
    }

    public void handleSubmitBet(ActionEvent actionEvent) {
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
