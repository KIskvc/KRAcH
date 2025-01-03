package at.ac.fhcampuswien.blackjack.models;

public abstract class BasePlayer {
    protected String name;
    protected Hand hand;

    public BasePlayer(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public abstract void playTurn(Game game);
}
