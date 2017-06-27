package controllers.player;

import org.junit.Test;
import player.Player;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-06-02.
 */
public class HumanPlayerControllerTest {
    private Player player;
    private HumanPlayerController humanPlayerController;
    @Test
    public void initiateGame() throws Exception {
        player = new Player();
        humanPlayerController = new HumanPlayerController();
        humanPlayerController.getPlayer().getDeck().populateDeck("C:\\Users\\luckyfang0601\\Documents\\SCHOOL\\comp354\\project\\pokemon\\res\\deck\\deck1.txt");
        humanPlayerController.buildViewController();
        humanPlayerController.initiateGame();
        assertEquals(7, humanPlayerController.getHandController().getContainer().getNoOfCards());
        assertEquals(47,humanPlayerController.getDeckController().getCardContainer().getNoOfCards());

    }

}