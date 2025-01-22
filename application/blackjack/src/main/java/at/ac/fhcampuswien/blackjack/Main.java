package at.ac.fhcampuswien.blackjack;

import at.ac.fhcampuswien.blackjack.models.BasePlayer;
import at.ac.fhcampuswien.blackjack.models.Dealer;
import at.ac.fhcampuswien.blackjack.models.Game;
import at.ac.fhcampuswien.blackjack.models.Player;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BasePlayer playerCurrent = null;
        ArrayList<Player> player = new ArrayList<>();
        final int STARTBALANCE = 1000;
        Dealer dealer = new Dealer("Mr.MakeYouBroke");
        Game game = new Game(player, dealer);
        Player player1 = new Player("Rana", STARTBALANCE);
        Player player2 = new Player("Kenan", STARTBALANCE);
        Player player3 = new Player("Harun", STARTBALANCE);
        player.add(player1);
        player.add(player2);
        player.add(player3);
        playerCurrent = setNextPlayer(playerCurrent, player, dealer);
        System.out.println(playerCurrent.getName());
        playerCurrent = setNextPlayer(playerCurrent, player, dealer);
        System.out.println(playerCurrent.getName());
        playerCurrent = setNextPlayer(playerCurrent, player, dealer);
        System.out.println(playerCurrent.getName());
        playerCurrent = setNextPlayer(playerCurrent, player, dealer);
        System.out.println(playerCurrent.getName());
        playerCurrent = setNextPlayer(playerCurrent, player, dealer);
        System.out.println(playerCurrent.getName());
        playerCurrent = setNextPlayer(playerCurrent, player, dealer);
        System.out.println(playerCurrent.getName());
        playerCurrent = setNextPlayer(playerCurrent, player, dealer);
        System.out.println(playerCurrent.getName());
        playerCurrent = setNextPlayer(playerCurrent, player, dealer);
        System.out.println(playerCurrent.getName());
        playerCurrent = setNextPlayer(playerCurrent, player, dealer);
        System.out.println(playerCurrent.getName());

    }


    public static BasePlayer setNextPlayer(BasePlayer playerCurrent, ArrayList<Player> player, Dealer dealer) {
        try {
            if (playerCurrent == null || playerCurrent instanceof Dealer) {
                playerCurrent = player.getFirst();

            } else {
                int indexOfCurrentPlayer = player.indexOf((Player)playerCurrent);
                int indexOfNewPlayer = indexOfCurrentPlayer + 1;
                if (indexOfNewPlayer >= player.size()) {
                    return dealer;
                } else {
                    playerCurrent = player.get(indexOfNewPlayer);
                }
            }
            return playerCurrent;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}