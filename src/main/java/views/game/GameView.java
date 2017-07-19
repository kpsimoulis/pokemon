package views.game;

import board.Board;
import controllers.game.KeyListeners.KeyDispatcher;
import views.activepokemon.ActivePokemonView;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.cardpiles.DeckView;
import views.cardpiles.DiscardPileView;
import views.cardpiles.PrizeCardView;
import views.coin.CoinView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;


public class GameView extends JFrame {

    private Board board;
    private KeyDispatcher dispatcher;

    public GameView() {

        board = new Board();
        setContentPane(board.$$$getRootComponent$$$());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pokemon TCG Game");
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

    }

    public Board getBoard() {
        return board;
    }

    public void setPlayerViews(DeckView deckView, HandView handView, DiscardPileView discardPileView,
                               BenchView benchView, CoinView coinView, PrizeCardView prizeCardView) {

        board.getPlayerDeckPanel().add(deckView).revalidate();
        board.getPlayerHandCards().add(handView).revalidate();
        board.getPlayerDiscardPanel().add(discardPileView).revalidate();
        board.getPlayerBenchCards().add(benchView).revalidate();
        board.getPlayerCoinPanel().add(coinView);
        board.getPlayerCoinPanel().revalidate();
        board.getPlayerPrizePanel().add(prizeCardView).revalidate();

    }

    public void setOpponentViews(DeckView deckView, HandView handView, DiscardPileView discardPileView,
                                 BenchView benchView, CoinView coinView, PrizeCardView prizeCardView) {

        board.getOpponentDeckPanel().add(deckView).revalidate();
        board.getOpponentHandCards().add(handView).revalidate();
        board.getOpponentDiscardPanel().add(discardPileView).revalidate();
        board.getOpponentBenchCards().add(benchView).revalidate();
        board.getOpponentCoinPanel().add(coinView);
        board.getOpponentCoinPanel().revalidate();
        board.getOpponentPrizePanel().add(prizeCardView).revalidate();

    }

    public void setCommand(String command) {

      board.getCmdTextArea().setText(command);


    }



    public void setPlayerActive(ActivePokemonView pokemonView) {
        board.getPlayerActivePanel().add(pokemonView).revalidate();
    }

    public void setOpponentActive(ActivePokemonView pokemonView){
        board.getOpponentActivePanel().removeAll();
        board.getOpponentActivePanel().add(pokemonView).revalidate();
    }

    public void addBoardListerner(KeyListener listener) {

        disableKeyListener();

        dispatcher = new KeyDispatcher(listener);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher( dispatcher );
    }

    public void disableKeyListener() {

        for(KeyListener listener: getKeyListeners()){
            removeKeyListener(listener);
        }

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.removeKeyEventDispatcher( dispatcher );

    }
}
