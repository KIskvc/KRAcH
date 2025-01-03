package at.ac.fhcampuswien.blackjack.models;

public class Dealer extends BasePlayer {

    public Dealer(String name) {
        super(name);
    }

    @Override
    public void playTurn(Game game) {
        Deck deck = game.getDeck();
        while(true) {
            if (super.hand.getCurrentScore() < 17) {
                super.hand.addCard(deck.dealCard());
            } else {
                break;
            }
        }
        game.setDeck(deck);
    }
}
