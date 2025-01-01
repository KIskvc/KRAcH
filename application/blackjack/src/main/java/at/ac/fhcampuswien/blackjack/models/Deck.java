package at.ac.fhcampuswien.blackjack.models;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;


public class Deck {
    private ArrayList<Card> deck;
    private Random random;

    public Deck() {
        this.deck = new ArrayList<>();
        this.random = new Random();

        String[] rank = {"A","Q","K","J","2","3","4","5","6","7","8","9","10"};
        String[] suits = {"CLUBS","DIAMONDS","HEARTS","SPADES"};

        for(int i=0; i<rank.length; i++){
            for(int j=0; j<suits.length; j++){
                this.deck.add(new Card(rank[i],suits[j])); // Karten im Devk erstellem
            }
        }
    }

    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    public void shuffleDeck(){
        Collections.shuffle(this.deck);
    }

    public Card dealCard(){
        Card firstCard = this.deck.getFirst();
        this.deck.removeFirst();
        return firstCard;
    }
}