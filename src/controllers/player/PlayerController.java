package controllers.player;

import card.Card;
import cardcontainer.CardContainer;
import controllers.activepokemon.ActivePokemonController;
import controllers.card.CardController;
import controllers.card.ControllerViewBuilder;
import controllers.cardcontainer.BenchController;
import controllers.cardcontainer.HandController;
import controllers.cardpiles.DeckController;
import controllers.cardpiles.DiscardPileController;
import controllers.coin.CoinController;
import javafx.util.Pair;
import player.Player;
import views.card.CardView;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.cardpiles.DeckView;
import views.cardpiles.DiscardPileView;
import views.coin.CoinView;

import static controllers.card.ControllerViewBuilder.buildControllerView;

public abstract class PlayerController {

    private Player player;
    private DeckController deckController;
    private HandController handController;
    private ActivePokemonController activePokemonController;
    private DiscardPileController discardPileController;
    private BenchController benchController;
    private CoinController coinController;

    public PlayerController(){

        this.player = new Player();
        activePokemonController = null;

    }

    public void buildViewController(){

        if (player==null || player.getDeck() == null | player.getDeck().getNoOfCards() == 0){
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

    }

    public void initiateGame(){

        if (!player.getDeck().validate()) {
            System.out.println("Incorrect Deck for Player " + player.getName());
            System.exit(0);
        } else {
            for (int i = 0; i < 7; i++) {
                handController.addCard(deckController.dealCard().getKey().getCard());
            }
            Card [] prizeCards = new Card[6];
            for (int i = 0; i < 6; i++) {
                prizeCards[i] = player.getDeck().dealCard();
            }
            player.setPrizeCards(prizeCards);
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
}
