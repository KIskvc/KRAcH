package at.ac.fhcampuswien.blackjack.models;

import java.util.ArrayList;

public class Player {
    private String name;
    private int balance;
    private ArrayList<Card> hand;

    public Player(String name, int balance) {
        this.name = name;
        this.balance = balance;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int placeBet(int betAmount) {
        if (betAmount > 0 && betAmount <= balance) {
            balance -= betAmount;
            return betAmount;
        } else {
            System.out.println("Ungültiger Einsatzbetrag.");
            return 0;
        }
    }

    public void playTurn() {
        System.out.println(name + " ist an der Reihe.");
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }
}

