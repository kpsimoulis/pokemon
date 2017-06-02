package controllers.player;

import cardcontainer.Deck;
import org.junit.Test;
import player.Player;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-06-01.
 */
public class PlayerControllerTest {
    private Player player;
    private PlayerController playerController;


    @Test
    public void initiateGame() throws Exception {
        player = new Player();
        playerController = new PlayerController();
        playerController.getPlayer().getDeck().populateDeck("C:\\Users\\luckyfang0601\\Documents\\SCHOOL\\comp354\\project\\pokemon\\res\\deck\\deck1.txt");
        playerController.buildViewController();
        playerController.initiateGame();
        assertEquals(7, playerController.getHandController().getContainer().getNoOfCards());
        assertEquals(47,playerController.getDeckController().getCardContainer().getNoOfCards());

    }
}

