package controllers.player;

import card.Card;
import card.Energy;
import card.Pokemon;
import controllers.activepokemon.ActivePokemonController;
import controllers.card.PokemonController;
import main.Ability;
import main.Attack;
import main.Requirement;
import main.Retreat;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import views.activepokemon.ActivePokemonView;
import views.card.PokemonView;

import javax.print.attribute.standard.PDLOverrideSupported;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by luckyfang0601 on 2017-06-02.
 */
public class AIPlayerControllerTest {
    private Player player;
    private AIPlayerController aiPlayerController;
    private Pokemon pokemon;
    private Boolean choosePokemon;
    private Boolean putSuccess1;
    private Boolean putSuccess2;
    private ActivePokemonController opponentPokemon;
    private PokemonController pokemonController;
    private ActivePokemonView activePokemonView;
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
    @Test
    public void putPokemonOnBench() throws Exception {
        player = new Player();
        aiPlayerController = new AIPlayerController();
        aiPlayerController.getPlayer().getDeck().populateDeck("res\\deck\\deck1.txt");
        aiPlayerController.buildViewController();
        aiPlayerController.initiateGame();
        aiPlayerController.putPokemonOnBench();
        if(aiPlayerController.getHandController().getContainer().getNoOfCards()<7)
            putSuccess1 = true;
        assertTrue(putSuccess1);
        if(aiPlayerController.getBenchController().getContainer().getNoOfCards()>0)
            putSuccess2=true;
        assertTrue(putSuccess2);
    }
 @Test
    public void attack() throws Exception {
     ArrayList<Energy> energyArray= new ArrayList<Energy>(20);
     Retreat retreat = new Retreat("fighting",1);
     Ability ability = new Ability("Rain Splash","damage","put 20 damage points on opponent","opponent-active");
     Requirement requirement=new Requirement("general",2);
     ArrayList<Requirement> requirements = new ArrayList<Requirement>();
     requirements.add(requirement);
     Attack attack = new Attack(requirements,ability);
     ArrayList<Attack> attacks = new ArrayList<Attack>();
     attacks.add(attack);
     Pokemon pokemon1 = new Pokemon("Raichu", 27, "pokemon", 90,energyArray, "basic","",retreat,attacks);
     PokemonView pokemonView = new PokemonView(energyArray,attacks,0,90,"basic");
     activePokemonView= new ActivePokemonView(pokemonView);
     Energy energy1 = new Energy("Fight",20,"fight");
     Energy energy2 = new Energy("Psychic",22,"psychic");
     pokemonController = new PokemonController(pokemon1);
     opponentPokemon = new ActivePokemonController(pokemonController,activePokemonView);
     player = new Player();
     aiPlayerController = new AIPlayerController();
     aiPlayerController.getPlayer().getDeck().populateDeck("res\\deck\\deck1.txt");
     aiPlayerController.buildViewController();
     aiPlayerController.initiateGame();
     aiPlayerController.setActivePokemon(true);
     aiPlayerController.attack(opponentPokemon);
     Pokemon pokemon2 = (Pokemon)opponentPokemon.getPokemonController().getCard();
     assertTrue(pokemon2.getDamagePoints()>0);


 }


}