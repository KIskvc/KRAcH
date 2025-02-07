package at.ac.fhcampuswien.blackjack.models;
import java.util.ArrayList;

//ZWISCHENSTAND pls dont judge !!! -lg anna

public class Hand {

    private ArrayList<Card> cards;;
    private ArrayList<ArrayList<Card>> splitCards;;
    private int currentScore;

    public Hand() {
        this.cards = new ArrayList<>();
        this.currentScore = getCurrentScore();
    }

    public int getCurrentScore() {
        int score = 0;
        int aceCount = 0;

        //Berechnung des Punktestandes basierend auf den Karten in der Hand (wichtig wenn Ass vorhanden)
        for (Card card : cards) {
            int value = card.getValue(); //getValue von Klasse Card
            if (value == 11) { //Ass zählt standardmäßig als 11
                aceCount++;
            }
            score += value;
        }

        //Ass-Wertung wird angepasst, wenn der Punktestand über 21 liegt
        while (score > 21 && aceCount > 0) {
            score -= 10; //reduziert den Ass-Wert auf 1
            aceCount--;
        }

        currentScore = score; //aktualisiert den gespeicherten Punktestand
        return currentScore;
    }

    public void addCard(Card card) {
            this.cards.add(card); //fügt die Karte zur Hand hinzu
            //return getCurrentScore(); //aktualisiert den Punktestand
    }

    public void split(Card card) {
            //überprüfen, ob ein Split möglich ist: genau 2 Karten mit gleichem Wert
        if (cards.size() == 2 && cards.get(0).getValue() == cards.get(1).getValue()) {
            splitCards = new ArrayList<>();
            ArrayList<Card> playersFirstHand = new ArrayList<>();
            ArrayList<Card> playersSecondHand = new ArrayList<>();

            playersFirstHand.add(cards.get(0));
            playersSecondHand.add(cards.get(1));

            splitCards.add(playersFirstHand);
            splitCards.add(playersSecondHand);

            cards.clear();
        }
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public ArrayList<ArrayList<Card>> getSplitCards(){
        return splitCards;
    }

}
