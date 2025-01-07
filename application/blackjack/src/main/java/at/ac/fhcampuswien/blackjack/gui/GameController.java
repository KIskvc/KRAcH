package at.ac.fhcampuswien.blackjack.gui;

import at.ac.fhcampuswien.blackjack.models.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Objects;

public class GameController {

    @FXML
    private HBox firstHand;
    @FXML
    private HBox secondHand;
    @FXML
    private HBox thirdHand;
    @FXML
    private HBox dealerHand;

    private ArrayList<Player> player = new ArrayList<>();
    private static final int STARTBALANCE = 1000;
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
        initGame();
    }

    @FXML
    public void hit() {
        Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource("/cards/2_of_spades.png")).toExternalForm());
        ImageView newCard = new ImageView();
        newCard.setFitWidth(50);
        newCard.setPickOnBounds(true);
        newCard.setPreserveRatio(true);
        newCard.setImage(newCardImage);
        firstHand.getChildren().add(newCard);
    }

    public void initGame() throws InterruptedException {
        game.initializeGame();
        Deck currentDeck = game.getDeck();
        for(int i = 0; i < 2; i++) {
            int handcounter = 0;
            for(Player player : game.getPlayer()) {
                Hand currentPlayerHand = player.getHand();
                Card currentCard = currentDeck.dealCard();
                currentPlayerHand.addCard(currentCard);
                //player.setHand(currentPlayerHand);
                dealCardToPlayer(player, currentCard, handcounter);
                game.setDeck(currentDeck);
                handcounter++;
                //wait(1000,0);
            }
            //this.Dealer.hand.addCard(Deck.dealCard());
        }
    }

    public void dealCardToPlayer(Player player, Card card, int handIndex) {
        HBox currentHbox;
        try {
            if(handIndex == 0) {
                currentHbox = firstHand;
            } else if(handIndex == 1) {
                currentHbox = secondHand;
            } else {
                currentHbox = thirdHand;
            }
            String path = card.getImage();
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
}
