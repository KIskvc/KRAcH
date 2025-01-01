package at.ac.fhcampuswien.blackjack.models;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> Player;
    private Object Dealer;
    private Deck Deck;

    public Game(int pot, ArrayList<Player> player, Object dealer) {
        Player = player;
        Dealer = dealer;
    }

    public ArrayList<?> getPlayer() {
        return Player;
    }

    public void initializeGame() {
        this.Deck = new Deck();
        this.Deck.shuffleDeck();

        for(int i = 0; i < 2; i++) {
            for(Player player : Player) {
                player.hand.addCard(Deck.dealCard());
            }
            // Dealer Code
        }
    }

    public void playRound() {
        for(Player player : Player) {
            player.playTurn();
        }
    }

    public void determineWinner() {
        // Check if dealer won.
    }

    public void leaveGame() {

    }
}
