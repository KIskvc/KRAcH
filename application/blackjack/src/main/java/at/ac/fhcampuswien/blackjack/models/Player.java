public class Player extends BasePlayer {
    private int balance;

    public Player(String name, int balance) {
        super(name);
        this.balance = balance;
    }

    // Getters and Setters
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    // Methods
    public void placeBet(int amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println(name + " placed a bet of " + amount);
        } else {
            System.out.println("Insufficient balance to place the bet.");
        }
    }


    @Override
    public void playTurn() {
        System.out.println(name + " is playing their turn.");
    }

    public void hit() {
        System.out.println(name + " chooses to hit.");
    }

    public void stand() {
        System.out.println(name + " chooses to stand.");
    }

    public void split() {
        System.out.println(name + " chooses to split.");
    }

    public void doubleBet() {
        System.out.println(name + " chooses to double the bet.");
    }
}
