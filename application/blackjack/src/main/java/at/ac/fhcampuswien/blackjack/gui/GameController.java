package at.ac.fhcampuswien.blackjack.gui;

import at.ac.fhcampuswien.blackjack.models.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import javafx.event.ActionEvent;
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
    private Button newRoundButton;
    @FXML
    private Group controlGroup;
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
    private TextField firstPlayerName;
    @FXML
    private TextField secondPlayerName;
    @FXML
    private TextField thirdPlayerName;
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
    private BasePlayer currentPlayer;
    private Game game;
    private Iterator<Player> playerIterator;
    private Map<Player, List<Hand>> playerHands = new HashMap<>();

    @FXML
    public void initialize() throws InterruptedException {
        Dealer dealer = new Dealer("Mr.MakeYouBroke");
        Player player1 = new Player("Rana", STARTBALANCE);
        Player player2 = new Player("Kenan", STARTBALANCE);
        Player player3 = new Player("Harun", STARTBALANCE);
        player.add(player1);
        firstPlayerName.setText("1. " + player1.getName());
        player.add(player2);
        secondPlayerName.setText("2. " + player2.getName());
        player.add(player3);
        thirdPlayerName.setText("3. " + player3.getName());
        game = new Game(player, dealer);
        playerIterator = player.iterator();
    }

    //---Kenans---
    //Change currentPlayer to next Player.
    public void setNextPlayer1() {
        try {
            if (currentPlayer == null || currentPlayer instanceof Dealer) {
                currentPlayer = player.getFirst();
            } else {
                int indexOfCurrentPlayer = player.indexOf((Player) currentPlayer);
                int indexOfNewPlayer = indexOfCurrentPlayer + 1;
                if (indexOfNewPlayer >= player.size()) {
                    currentPlayer = game.getDealer();
                } else {
                    currentPlayer = player.get(indexOfNewPlayer);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean hasNextPlayer(Player playerObject) {
        int indexOfCurrentPlayer = player.indexOf(playerObject);
        return indexOfCurrentPlayer != -1 && indexOfCurrentPlayer < player.size() - 1;
    }

    public void goToNextPlayer() {
        Player playerCurrent = (Player) currentPlayer;
        boolean hasNext = hasNextPlayer(playerCurrent);
        if (hasNext) {
            setNextPlayer1();
            statusTextField.setText(currentPlayer.getName() + "'s turn.");
        } else {
            controlGroup.setVisible(false);
            playDealerTurn();
        }
    }

    //---Haruns----
    //Change currentPlayer to next Player.
//    public void setNextPlayer() {
//        try {
//            if (playerCurrent == null) {
//                playerCurrent = player.get(0);
//            } else {
//                int indexOfCurrentPlayer = player.indexOf(playerCurrent);
//                if (indexOfCurrentPlayer < player.size() - 1) {
//                    playerCurrent = player.get(indexOfCurrentPlayer + 1);
//                } else {
//                    playerCurrent = null;
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Error occurred with switching player: " + e.getMessage());
//        }
//    }

    @FXML
    public void initGame() throws InterruptedException {
        playButton.setVisible(false);
        game.initializeGame();
        setNextPlayer1();

        placeBetText.setText(currentPlayer.getName() + ", place your bet!");
        placeBetBox.setVisible(true);
    }

    @FXML
    public boolean handleBet() {
        if (currentPlayer instanceof Dealer) {
            return true;
        }

        Player playerInTurn = (Player) currentPlayer;

        String betText = betTextField.getText();
        int bet;

        if (betText.isEmpty()) {
            errLabel.setText("Please place a bet");
            return false;
        }

        try {
            bet = Integer.parseInt(betText);  // Text in Zahl konvertieren
        } catch (NumberFormatException e) {
            errLabel.setText("Invalid! Please enter a number!");
            return false;
        }

        if (bet <= 0) {
            errLabel.setText("Invalid bet! Please enter a positive number!");
            return false;
        } else if (bet > playerInTurn.getBalance()) {
            errLabel.setText("Brokieee! Place a valid bet!");
            return false;
        }

        playerInTurn.setCurrentBet(bet);
        playerInTurn.placeBet(bet);

        betTextField.clear();
        errLabel.setText("");

        setNextPlayer1();
        if (!(currentPlayer instanceof Dealer)) {
            placeBetText.setText(currentPlayer.getName() + ", place your bet!");
            return false;
        }
        return true;
    }

    public void handleSubmitBet() {
        boolean done = handleBet();
        if (done) {
            placeBetBox.setVisible(false);
            dealCards();
        }
    }

    public void dealCards() {
        firstHand.setSpacing(-25);
        secondHand.setSpacing(-25);
        thirdHand.setSpacing(-25);
        dealerHand.setSpacing(-25);
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
        playRound();
    }

    public void dealCardToPlayer(BasePlayer player, Deck deck, int handIndex) {

        Hand currentPlayerHand = player.getHand();
        Card currentCard = deck.dealCard();
        HBox currentHbox;
        String path;
        Image newCardImage;

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

            if (player instanceof Dealer && !currentPlayerHand.getCards().isEmpty() && currentPlayerHand.getCurrentScore() != 10 && currentPlayerHand.getCurrentScore() != 11) {
                Dealer dealer = (Dealer) player;
                path = currentCard.getBackImage();
                newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
            } else {
                path = currentCard.getImage();
                newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
            }

            currentPlayerHand.addCard(currentCard);

            addCardToHand(currentHbox, newCardImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addCardToHand(HBox hand, Image image) {
        ImageView newCard = new ImageView();
        newCard.setFitWidth(50);
        newCard.setPickOnBounds(true);
        newCard.setPreserveRatio(true);
        newCard.setImage(image);
        hand.getChildren().add(newCard);
    }

    public void playRound () {
        Dealer dealer = game.getDealer();
        if (dealer.hasBlackJack()) {
            handleDealerWin();
//            Timer timer = new Timer(3000, e -> {
//                Platform.runLater(this::handleDealerWin);
//            });
//            timer.setRepeats(false);
//            timer.start();
            return;
        }
        controlGroup.setVisible(true);
        setNextPlayer1();
        statusTextField.setVisible(true);
        statusTextField.setText(currentPlayer.getName() + "'s turn.");
    }

    public void playDealerTurn () {
        statusTextField.setText("Dealer's Turn.");
        revealDealerCard();
        Dealer dealer = game.getDealer();
        while (dealer.getHand().getCurrentScore() < 17) {
            Hand currentPlayerHand = dealer.getHand();
            Card currentCard = game.getDeck().dealCard();
            String path = currentCard.getImage();
            Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
            currentPlayerHand.addCard(currentCard);
            addCardToHand(dealerHand, newCardImage);
        }
        evaluateWinners();
    }

    public void evaluateWinners () {
        for (Player player : game.getPlayer()) {
            if (player.getHand().getCurrentScore() > game.getDealer().getHand().getCurrentScore()) {
                player.setBalance(player.getBalance() + player.getCurrentBet() * 2);
            } else if (player.getHand().getCurrentScore() == game.getDealer().getHand().getCurrentScore()) {
                player.setBalance(player.getBalance() + player.getCurrentBet());
            }
        }
        newRoundButton.setVisible(true);
        //reinitializeGame();
    }
    public void handleDealerWin () {
        statusTextField.setText("The bank wins .");
        for (Player player : game.getPlayer()) {
            if (player.getHand().getCurrentScore() == 21) {
                player.setBalance(player.getBalance() + player.getHand().getCurrentScore());
            }
        }
        newRoundButton.setVisible(true);

        //reinitializeGame();
    }

    public void reinitializeGame () {
        game.initializeGame();
        firstHand.getChildren().clear();
        secondHand.getChildren().clear();
        thirdHand.getChildren().clear();
        dealerHand.getChildren().clear();

        for (Player player : game.getPlayer()) {
            player.setHand(new Hand());
        }
        statusTextField.setText("");
        placeBetText.setText(currentPlayer.getName() + ", place your bet!");
        placeBetBox.setVisible(true);
        controlGroup.setVisible(false);
        newRoundButton.setVisible(false);
    }

    public void revealDealerCard () {
        dealerHand.getChildren().clear();
        for (Card card : game.getDealer().getHand().getCards()) {
            String path = card.getImage();
            Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
            addCardToHand(dealerHand, newCardImage);
        }
    }

    @FXML
    public void hit() {
        Player playerCurrent = (Player) currentPlayer;

        if (playerCurrent != null) {
            HBox currentHand;
            Card drawnCard = playerCurrent.hit(game.getDeck());

            int currentIndex = player.indexOf(playerCurrent);
            if (currentIndex == 0) {
                currentHand = firstHand; // Rana
            } else if (currentIndex == 1) {
                currentHand = secondHand; // Kenan
            } else {
                currentHand = thirdHand; // Harun
            }

            String path = drawnCard.getImage();
            Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());

            addCardToHand(currentHand, newCardImage);

            int handValue = playerCurrent.getHand().getCurrentScore();

            if (handValue < 21) {
                updateScore(playerCurrent);
            } else if (handValue == 21) {
                //statusTextField.setText(playerCurrent.getName() + " has BlackJack!");
                System.out.println(playerCurrent.getName() + " has BlackJack!");
                goToNextPlayer();
            } else {
                //statusTextField.setText(playerCurrent.getName() + " has a Bust!");
                System.out.println(playerCurrent.getName() + " has a Bust!");
                goToNextPlayer();
            }
        } else {
            statusTextField.setText("Kein Spieler aktiv. Bitte starte das Spiel.");
        }
    }

        @FXML
        public void doubleDown () {
        // noch nicht fertig
        Player playerCurrent = (Player) currentPlayer;
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

                    setNextPlayer1();

                } catch (IllegalStateException e) {
                    errLabelDouble.setText("Error: " + e.getMessage());
                }
            }
        }

        @FXML
        public void handleStandButton (ActionEvent event){
        Player playerCurrent = (Player) currentPlayer;
        playerCurrent.stand();
        goToNextPlayer();

//        if (playerCurrent != null) {
//            playerCurrent.stand();
//            setNextPlayer1();
//
//            if (playerCurrent != null) {
//                statusTextField.setText(playerCurrent.getName() + " ist an der Reihe.");
//                hit.setDisable(false);
//            } else {
//                statusTextField.setText("Alle Spieler sind fertig. Dealer ist dran!");
//                hit.setDisable(true);
//                Dealer dealer = game.getDealer();
//                dealer.playTurn(game);
//                revealDealerCard();
//            }
//        } else {
//            statusTextField.setText("Kein Spieler aktiv. Bitte starte das Spiel.");
//        }
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

    public void updateScore(Player playerObject) {
        int currentIndex = player.indexOf(playerObject);

        if (currentIndex == 0) {
        } else if (currentIndex == 1) {
            } else {
            }
    }

    @FXML
    public void handleLeaveButton (ActionEvent event){
        SceneManager.getInstance().switchScene("main-view.fxml");
    }
}
