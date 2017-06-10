package controllers.player;

import card.Energy;
import card.Pokemon;
import controllers.activepokemon.ActivePokemonController;
import controllers.card.CardController;
import controllers.cardcontainer.BenchController;
import controllers.cardcontainer.HandController;
import controllers.cardpiles.DeckController;
import controllers.cardpiles.DiscardPileController;
import controllers.cardpiles.PrizeCardController;
import controllers.coin.CoinController;
import main.Attack;
import main.Requirement;
import player.Player;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.cardpiles.DeckView;
import views.cardpiles.DiscardPileView;
import views.cardpiles.PrizeCardView;
import views.coin.CoinView;

import java.util.HashMap;

public abstract class PlayerController {

    private Player player;
    private DeckController deckController;
    private HandController handController;
    private ActivePokemonController activePokemonController;
    private DiscardPileController discardPileController;
    private BenchController benchController;
    private CoinController coinController;
    private PrizeCardController prizeCardController;

    public PlayerController() {

        this.player = new Player();
        activePokemonController = null;

    }

    public void buildViewController() {

        if (player == null || player.getDeck() == null | player.getDeck().getNoOfCards() == 0) {
            System.out.println("Cannot build the views and controllers");
            System.exit(0);
        }

        DeckView deckView = new DeckView();
        deckController = new DeckController(player.getDeck(), deckView);

        HandView handView = new HandView();
        handController = new HandController(player.getHand(), handView);

        DiscardPileView discardPileView = new DiscardPileView();
        discardPileController = new DiscardPileController(player.getDiscardPile(), discardPileView);

        BenchView benchView = new BenchView();
        benchController = new BenchController(player.getBench(), benchView);

        CoinView coinView = new CoinView();
        coinController = new CoinController(player.getPlayerCoin(), coinView);

        PrizeCardView prizeCardView = new PrizeCardView();
        prizeCardController = new PrizeCardController(player.getPrizeCards(), prizeCardView);

    }

    public void setActivePokemonController(ActivePokemonController activePokemonController) {
        this.activePokemonController = activePokemonController;
    }

    public void initiateGame() {

        if (!player.getDeck().validate()) {
            System.out.println("Incorrect Deck for Player " + player.getName());
            System.exit(0);
        } else {
            for (int i = 0; i < 7; i++) {
                handController.addCard(deckController.dealCard().getKey().getCard());
            }
            for (int i = 0; i < 6; i++) {
                prizeCardController.addCard(player.getDeck().dealCard());
            }
        }

    }

    public Player getPlayer() {
        return player;
    }

    public DeckController getDeckController() {
        return deckController;
    }

    public HandController getHandController() {
        return handController;
    }

    public ActivePokemonController getActivePokemonController() {
        return activePokemonController;
    }

    public DiscardPileController getDiscardPileController() {
        return discardPileController;
    }

    public BenchController getBenchController() {
        return benchController;
    }

    public CoinController getCoinController() {
        return coinController;
    }

    public PrizeCardController getPrizeCardController() {
        return prizeCardController;
    }

    public boolean handHasPokemon() {

        for (CardController cardController : getHandController().getCardControllers()) {
            if (cardController.getCard() instanceof Pokemon) {
                return true;
            }
        }
        return false;

    }

    public boolean handHasEnergy() {

        for (CardController cardController : getHandController().getCardControllers()) {
            if (cardController.getCard() instanceof Energy) {
                return true;
            }
        }
        return false;
    }

    public boolean canAttack() {

        Pokemon pokemon = (Pokemon) getActivePokemonController().getPokemonController().getCard();
        HashMap<String, Integer> dict = getActivePokemonController().getEnergyOnCard();
        for (Attack attack : pokemon.getAttack()){
            for (Requirement requirement : attack.getRequirement()) {
                if (dict.containsKey(requirement.getCategory()) && dict.get(requirement.getCategory()) == requirement.getEnergyAmount()) {
                    return true;
                }
            }
        }

        return false;

    }

    public void dealDeck(){
        getHandController().addCard(getDeckController().dealCard().getKey().getCard());
    }

    public Pokemon getActivePokemonCard(){
        return (Pokemon) getActivePokemonController().getPokemonController().getCard();
    }

}
