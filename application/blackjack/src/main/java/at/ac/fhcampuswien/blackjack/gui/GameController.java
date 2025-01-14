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
    private TextField statusTextField;
    @FXML
    private Button hit;

    private ArrayList<Player> player = new ArrayList<>();
    private static final int STARTBALANCE = 1000;
    private Player playerCurrent;
    private Game game;
    int currentPlayer = 0;

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
        //initGame();
//        placeBetText.setText(player.getFirst().getName() + ", place your bet!");
//        placeBetBox.setVisible(true);
    }

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
    }

    @FXML
    public void hit() {
        //Variante 1
        Player currentPlayer = player.get(0);
        Card drawnCard = currentPlayer.hit(game.getDeck());

        //Variante 2
//    Hand currentHand = player.get(0).getHand();
//    Deck currentDeck = game.getDeck();
//    currentHand.addCard(currentDeck.dealCard());

        //Add Card to current player hand.
        //Update Deck
        String path = drawnCard.getImage();
        Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
        ImageView newCard = new ImageView();
        newCard.setFitWidth(50);
        newCard.setPickOnBounds(true);
        newCard.setPreserveRatio(true);
        newCard.setImage(newCardImage);
        firstHand.getChildren().add(newCard);

        // Check current hand value
        int handValue = currentPlayer.getHand().getCurrentScore();

        if (handValue < 21) {
            statusTextField.setText("Current score: " + handValue);
        } else if (handValue == 21) {
            statusTextField.setText("Blackjack! Maximum score reached.");
            hit.setDisable(true); // Disable the Hit button
        } else {
            statusTextField.setText("Busted! You lose.");
            hit.setDisable(true); // Disable the Hit button
        }

        // Additional safety: Disable the button if conditions are met
        if (handValue >= 21) {
            hit.setDisable(true);
        }
    }



    @FXML
    public void initGame() throws InterruptedException {
        playButton.setVisible(false);
        game.initializeGame();
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
    // Code block for placing bet

    @FXML
    public void handleLeaveButton(ActionEvent event) {
        SceneManager.getInstance().switchScene("main-view.fxml");
    }

    @FXML
    public void handleStandButton(ActionEvent event) {
        if (playerCurrent != null) {
            playerCurrent.stand();

            int currentIndex = player.indexOf(playerCurrent);
            if (currentIndex < player.size() - 1) {
                playerCurrent = player.get(currentIndex + 1);
            } else {
                playerCurrent = null;
                Dealer dealer = game.getDealer();
                dealer.playTurn(game);

                revealDealerCard();
            }
        }
    }
}