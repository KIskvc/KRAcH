package at.ac.fhcampuswien.blackjack.models;


public class Player extends BasePlayer {
    private int balance;
    private int currentBet;

    // Konstruktor
    public Player(String name, int balance) {
        super(name);
        this.balance = balance;
        this.currentBet = 0;
    }

    // Getter und Setter
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public void placeBet(int amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println(name + " placed a bet of " + amount + ". Current balance: " + balance);
        } else {
            System.out.println(name + " does not have enough balance to place the bet.");
        }
    }

    @Override
    public void playTurn(Game game) {
        System.out.println(name + " is playing their turn.");
    }

    public Card hit(Deck deck) {
        Card drawnCard = deck.dealCard();
        this.hand.addCard(drawnCard);
        System.out.println(name + " drew a card: " + drawnCard.getRank() + " of " + drawnCard.getSuit());
        return drawnCard;
    }

    public void stand() {
        System.out.println(name + " stands.");
    }

    public void split() {
        if (this.hand.getCards().size() == 2 &&
                this.hand.getCards().get(0).getValue() == this.hand.getCards().get(1).getValue()) {
            this.hand.split(this.hand.getCards().get(0));
            System.out.println(name + " split their hand.");
        } else {
            System.out.println(name + " cannot split their hand.");
        }
    }

    public void doubleBet(Deck deck, Game game) {
        int doubleAmount = currentBet;
        if (doubleAmount <= balance) {
            balance -= doubleAmount;
            currentBet += doubleAmount;
            System.out.println(name + " doubles the bet to " + doubleAmount);
            //hit(deck);
        } else {
            System.out.println(name + " does not have enough balance to double.");
        }
    }
}
