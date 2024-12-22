package at.ac.fhcampuswien.blackjack.models;

import java.util.ArrayList;

public class Game {

    private ArrayList<?> Player;
    private int Pot;
    private int MinimumBet;
    private int PlayerRaise;
    private Deck Deck;
    private int wasauchimmer;

    public Game(int pot, ArrayList<?> player, int minimumBet, Deck deck) {
        Pot = pot;
        Player = player;
        MinimumBet = minimumBet;
        Deck = deck;
    }

    public int getPot() {
        return Pot;
    }

    public void setPot(int pot) {
        Pot = pot;
    }

    public int getMinimumBet() {
        return MinimumBet;
    }

    public void setMinimumBet(int minimumBet) {
        MinimumBet = minimumBet;
    }

    public int getPlayerRaise() {
        return PlayerRaise;
    }

    public void setPlayerRaise(int playerRaise) {
        PlayerRaise = playerRaise;
    }

    public ArrayList<?> getPlayer() {
        return Player;
    }

    public void initializeGame() {

    }

    public void playRound() {

    }

//    public  determineWinner() {
//
//    }

    public void resetRound() {

    }

    public void leaveGame() {

    }
}
