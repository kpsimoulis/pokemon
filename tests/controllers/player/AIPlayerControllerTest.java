package controllers.player;

import card.Card;
import card.Pokemon;
import org.junit.Before;
import org.junit.Test;
import player.Player;

import javax.print.attribute.standard.PDLOverrideSupported;

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
        aiPlayerController.getPlayer().getDeck().populateDeck("C:\\Users\\luckyfang0601\\Documents\\SCHOOL\\comp354\\project\\pokemon\\res\\deck\\deck1.txt");
        aiPlayerController.buildViewController();
        aiPlayerController.initiateGame();
        assertEquals(7, aiPlayerController.getHandController().getContainer().getNoOfCards());
        assertEquals(47,aiPlayerController.getDeckController().getCardContainer().getNoOfCards());

    }
    @Test
    public void setActivePokemon() throws Exception {
        player = new Player();
        aiPlayerController = new AIPlayerController();
        aiPlayerController.getPlayer().getDeck().populateDeck("C:\\Users\\luckyfang0601\\Documents\\SCHOOL\\comp354\\project\\pokemon\\res\\deck\\deck1.txt");
        aiPlayerController.buildViewController();
        aiPlayerController.initiateGame();
        aiPlayerController.setActivePokemon(true);
        assertTrue(aiPlayerController.getPlayer().hasActivePokemon());
        assertNull(aiPlayerController.setActivePokemon(false));
    }

    @Test
    public void chooseActivePokemon() throws Exception {
        player = new Player();
        aiPlayerController = new AIPlayerController();
        aiPlayerController.getPlayer().getDeck().populateDeck("C:\\Users\\luckyfang0601\\Documents\\SCHOOL\\comp354\\project\\pokemon\\res\\deck\\deck1.txt");
        aiPlayerController.buildViewController();
        aiPlayerController.initiateGame();
        pokemon=aiPlayerController.chooseActivePokemon();
        for (Card card : aiPlayerController.getHandController().getContainer().getCards()) {
            if (card.equals(pokemon)) {
                choosePokemon=true;
            }
        }
        assertTrue(choosePokemon);



    }

}