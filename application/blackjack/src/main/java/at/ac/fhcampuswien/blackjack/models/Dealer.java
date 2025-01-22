package at.ac.fhcampuswien.blackjack.models;

import java.util.ArrayList;

public class Dealer extends BasePlayer {

    public Dealer(String name) {
        super(name);
    }

    @Override
    public void playTurn(Game game) {
        Deck deck = game.getDeck();
        while(super.getHand().getCurrentScore() < 17) {
            super.hand.addCard(deck.dealCard());
        }
    }
}
