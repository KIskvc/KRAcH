package at.ac.fhcampuswien.blackjack.models;

import javafx.scene.image.Image;

import java.nio.file.Path;
import java.util.Objects;

public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }
    public String getSuit() {
        return suit;
    }

    public int getValue() {
        switch (rank) {
            case "ace":
                return 11;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10":
                return 10;
            case "king":
                return 10;
            case "queen":
                return 10;
            case "jack":
                return 10;
            default:
                return 0;
        }
    }

    public String getImage(){
        String name = rank + "_of_" + suit + ".png";
        return "/cards/" + name;
        //Image newCardImage = new Image(Objects.requireNonNull(getClass().getResource("/cards/2_of_spades.png")).toExternalForm());
    }
    public String getBackImage(){
        return "/cards/back.png";
    }

}

