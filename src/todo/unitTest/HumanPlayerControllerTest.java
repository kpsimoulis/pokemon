package unitTest;

import controllers.player.HumanPlayerController;
import org.junit.Test;
import player.Player;

import static org.junit.Assert.assertEquals;

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
        humanPlayerController.getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
        humanPlayerController.buildViewController();
        humanPlayerController.initiateGame();
        assertEquals(7, humanPlayerController.getHandController().getContainer().getNoOfCards());
        assertEquals(47,humanPlayerController.getDeckController().getCardContainer().getNoOfCards());

    }

}