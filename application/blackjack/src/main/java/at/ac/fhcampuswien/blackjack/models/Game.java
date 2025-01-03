package at.ac.fhcampuswien.blackjack.models;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> Player;
    private Dealer Dealer;
    private Deck Deck;

    public Game(int pot, ArrayList<Player> player, Dealer dealer) {
        Player = player;
        Dealer = dealer;
    }

    public ArrayList<Player> getPlayer() {
        return Player;
    }

    public Deck getDeck() {
        return this.Deck;
    }

    public void setDeck(Deck deck) {
        Deck = deck;
    }

    public void initializeGame() {
        this.Deck = new Deck();
        this.Deck.shuffleDeck();

        for(int i = 0; i < 2; i++) {
            for(Player player : Player) {
                player.hand.addCard(Deck.dealCard());
            }
            this.Dealer.hand.addCard(Deck.dealCard());
        }
    }

    public void playRound() {
        if(determineDealerWin()) {
            resetGame();
            return;
        }
        for(Player player : Player) {
            player.playTurn(this);
        }
    }

    public boolean determineDealerWin() {
        if(this.Dealer.hand.getCurrentScore() == 21) {
            for(Player player : Player) {
                if(player.hand.getCurrentScore() != 21) {
                    int newBalance = player.getBalance() - player.getHand().getCurrentScore();
                    player.setBalance(newBalance);
                } else {
                    int newBalance = player.getBalance() + player.getHand().getCurrentScore();
                    player.setBalance(newBalance);
                }
            }
            System.out.println("The Dealer wins the round.");
            return true;
        }
        return false;
    }

    public void resetGame() {
        initializeGame();
    }

    public void leaveGame() {

    }
}
