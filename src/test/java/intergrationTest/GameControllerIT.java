package intergrationTest;

import card.Energy;
import card.Pokemon;
import controllers.game.GameController;
import parser.*;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import views.card.PokemonView;
import views.game.GameView;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by luckyfang0601 on 2017-07-14.
 */
public class GameControllerIT {
    private GameView view = new GameView();
    private boolean firstTurn;
    private boolean energyAdded;
    private Player player;
    private ArrayList<Energy> energyArray = new ArrayList<Energy>(2);
    private Retreat retreat = new Retreat("fighting", 1);
    ArrayList<AbilityLogic> logic = new ArrayList<AbilityLogic>();
    //logic.add(new Dam(new ArrayList<String>(Arrays.asList("dam:target:choice:opponent-bench:30"))));
    Ability ability = new Ability("Rain Splash","damage", logic);
    private Requirement requirement = new Requirement("general", 2);
    private ArrayList<Requirement> requirements = new ArrayList<Requirement>();
    private Attack attack = new Attack(requirements, ability);
    private ArrayList<Attack> attacks = new ArrayList<Attack>();
    private ArrayList<Pokemon> poks;
    private PokemonView pokemonView = new PokemonView(energyArray, attacks, 0, 90, "basic",1);
    private Pokemon pokemon = new Pokemon("Raichu", 27, "pokemon", 90, energyArray, "basic", "", retreat, attacks,poks);
    private Energy energy1 = new Energy("Fight", 20, "fight");

    private GameController gameControllerTest;


    @Before
    public void setUp() throws Exception {


        gameControllerTest = new GameController( );

        gameControllerTest.setEnergyAdded(energyAdded);
        gameControllerTest.setFirstTurn(firstTurn);
        view.setVisible(false);

//        player1Controller.getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
//        player1Controller.buildViewController();
//        player1Controller.initiateGame();
//
//        player2Controller.getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
//        player2Controller.buildViewController();
//        player2Controller.initiateGame();


    }

    @Test
    public void isFirstTurn() throws Exception {

        assertFalse(gameControllerTest.isFirstTurn());
        //TODO after first turn

    }

    @Test
    public void isEnergyAdded() throws Exception {
        assertFalse(gameControllerTest.isEnergyAdded());
        //TODO after first turn
    }

    @Test
    public void displayChoiceDialog() throws Exception {

        assertEquals(0, gameControllerTest.getHumanController().getPlayer().getDeck().getNoOfCards());
        gameControllerTest.getAIController().getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
        gameControllerTest.getHumanController().getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
        gameControllerTest.getAIController().getPlayer().setName("1");
        gameControllerTest.getHumanController().getPlayer().setName("2");
        gameControllerTest.displayChoiceDialog();
//        assertNotEquals(0, gameControllerTest.getHumanController().getPlayer().getDeck().getNoOfCards());

        assertTrue(gameControllerTest.getView().getBoard().getPlayerDeckPanel().isValid());
    }

    @Test
    public void loadBoard() throws Exception {

        gameControllerTest.getAIController().getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
        gameControllerTest.getHumanController().getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
        gameControllerTest.getAIController().getPlayer().setName("1");
        gameControllerTest.getHumanController().getPlayer().setName("2");

//        assertNull( gameControllerTest.getHumanController().getDeckController().getView());
//        assertEquals(0,gameControllerTest.getHumanController().getPlayer().getDeck().getNoOfCards());
//        assertEquals(442,gameControllerTest.getView().getHeight());
//        assertEquals(1,gameControllerTest.getView().getComponentCount());

        int heightBefore = gameControllerTest.getView().getHeight();
        assertNull(gameControllerTest.getHumanController().getDeckController());

        gameControllerTest.loadBoard();

        assertNotNull(gameControllerTest.getHumanController().getDeckController());
        int heightAfter = gameControllerTest.getView().getHeight();
        //assertNotEquals(heightAfter, heightBefore);


//        assertNotNull( gameControllerTest.getHumanController().getDeckController().getView());
//        assertFalse( gameControllerTest.getView().getBoard().getPlayerDeckPanel().isValid());
//        assertFalse(view.getBoard().getPlayerDeckPanel().isValid());
//        loadBoard();
//        assertNotNull(player1Controller.getDeckController().getView());


    }

    @Test
    public void playerChooseActive() throws Exception {
        gameControllerTest.getAIController().getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
        gameControllerTest.getHumanController().getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
        gameControllerTest.getAIController().getPlayer().setName("1");
        gameControllerTest.getHumanController().getPlayer().setName("2");
        gameControllerTest.getHumanController().buildViewController();
        gameControllerTest.getHumanController().getHandController().addCard(pokemon);
        gameControllerTest.getAIController().buildViewController();
        //    gameControllerTest.getAIController().getHandController().addCard(pokemon);
//        gameControllerTest.getAIController().initiateGame();

        assertFalse(gameControllerTest.isFirstTurn());
        gameControllerTest.playerChooseActive();
        assertTrue(gameControllerTest.isFirstTurn());


    }

//    @Test
//    public void endFirstTurn() throws Exception {
//        gameControllerTest.getAIController().getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
//        gameControllerTest.getHumanController().getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
//        gameControllerTest.getAIController().getPlayer().setName("1");
//        gameControllerTest.getHumanController().getPlayer().setName("2");
//        gameControllerTest.getHumanController().buildViewController();
//        gameControllerTest.getHumanController().getHandController().addCard(pokemon);
//        gameControllerTest.getAIController().buildViewController();
//        gameControllerTest.getAIController().getHandController().addCard(pokemon);
//        gameControllerTest.getAIController().initiateGame();
//        gameControllerTest.playerChooseActive();
//
//        assertTrue(gameControllerTest.isFirstTurn());
//        gameControllerTest.endFirstTurn();
//        assertFalse(gameControllerTest.isFirstTurn());
//
//
//    }

    @Test
    public void playerDealDeck() throws Exception {
        gameControllerTest.getHumanController().getPlayer().setName("2");
        gameControllerTest.getHumanController().getPlayer().getDeck().addCard(energy1);
        gameControllerTest.getHumanController().buildViewController();

        assertEquals(0, gameControllerTest.getHumanController().getHandController().getContainer().getNoOfCards());
        gameControllerTest.playerDealDeck(gameControllerTest.getHumanController());
        assertEquals(1, gameControllerTest.getHumanController().getHandController().getContainer().getNoOfCards());

    }



    @Test
    public void gameAITurn() throws Exception {
//        gameControllerTest.getHumanController().getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
//        gameControllerTest.getHumanController().getPlayer().setName("2");
//        gameControllerTest.getAIController().getPlayer().getDeck().populateDeck("src/main/resources/deck/deck1.txt");
//        gameControllerTest.getAIController().getPlayer().setName("1");
//        gameControllerTest.loadBoard();
//        gameControllerTest.getHumanController().getPlayer().setActivePokemon(pokemon);
//
//        gameControllerTest.getHumanController().getHandController().addCard(pokemon);
//        gameControllerTest.getAIController().getHandController().addCard(pokemon);
//
//        gameControllerTest.endFirstTurn();
//        gameControllerTest.getAIController().getPlayer().setActivePokemon(pokemon);
//        gameControllerTest.gameAITurn();
    }


}
