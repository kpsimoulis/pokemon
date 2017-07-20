package unitTest;

import card.Pokemon;
import controllers.player.AIPlayerController;
import org.junit.Test;
import player.Player;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-06-02.
 */
public class AIPlayerControllerTest {
    private Player player;
    private AIPlayerController aiPlayerController;
    private Pokemon pokemon;
    private Boolean choosePokemon;

    @Test
    public void initiateGame() throws Exception {
        player = new Player();
        aiPlayerController = new AIPlayerController();
        aiPlayerController.getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
        aiPlayerController.buildViewController();
        aiPlayerController.initiateGame();
        assertEquals(7, aiPlayerController.getHandController().getContainer().getNoOfCards());
        assertEquals(47, aiPlayerController.getDeckController().getCardContainer().getNoOfCards());

    }

    @Test
    public void setActivePokemon() throws Exception {
        player = new Player();
        aiPlayerController = new AIPlayerController();
        aiPlayerController.getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
        aiPlayerController.buildViewController();
        aiPlayerController.initiateGame();
        aiPlayerController.setActivePokemon(true);
        assertTrue(aiPlayerController.getPlayer().hasActivePokemon());
        assertNull(aiPlayerController.setActivePokemon(false));
    }


}