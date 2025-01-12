package at.ac.fhcampuswien.blackjack.gui;

import at.ac.fhcampuswien.blackjack.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
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
    @FXML
    private Button playButton;

    private ArrayList<Player> player = new ArrayList<>();
    private static final int STARTBALANCE = 1000;
    private Game game;
    private Player currentPlayer;

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
    }

    //Change currentPlayer to next Player.
    public void setNextPlayer() {
        try {
            if(currentPlayer == null) {
                currentPlayer = player.getFirst();

            } else {
                int indexOfCurrentPlayer = player.indexOf(currentPlayer);
                int indexOfNewPlayer = indexOfCurrentPlayer + 1;
                if(indexOfNewPlayer >= player.size()-1) {
                    indexOfNewPlayer = 0;
                }
                currentPlayer = player.get(indexOfNewPlayer);
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
//        Hand currentHand = player.get(0).getHand();
//        Deck currentDeck = game.getDeck();
//        currentHand.addCard(currentDeck.dealCard());

        //Add Card to current player hand.
        //Update Deck
        String path = drawnCard.getImage();
        Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
        ImageView newCard = new ImageView();
        newCard.setFitWidth(50);
        newCard.setPickOnBounds(true);
        newCard.setPreserveRatio(true);
        newCard.setImage(newCardImage);
        //Get hand from currentPlayer (e.g. firstHand) and add Card.
        //firstHand.getChildren().add(newCard);
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

    @FXML
    public void handleLeaveButton(ActionEvent event) {
        SceneManager.getInstance().switchScene("main-view.fxml");
    }
}
