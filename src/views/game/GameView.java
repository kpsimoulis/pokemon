package views.game;

import board.Board;
import card.Card;
import cardcontainer.CardContainer;
import controllers.game.GameController;
import views.activepokemon.ActivePokemonView;
import views.card.CardView;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.cardpiles.DeckView;
import views.cardpiles.DiscardPileView;
import views.coin.CoinView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class GameView extends JFrame {

    private Board board;

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
                               BenchView benchView, CoinView coinView) {

        board.getPlayerDeckPanel().add(deckView).revalidate();
        board.getPlayerHandCards().add(handView).revalidate();
        board.getPlayerDiscardPanel().add(discardPileView).revalidate();
        board.getPlayerBenchCards().add(benchView).revalidate();
        board.getPlayerCoinPanel().add(coinView);
        board.getPlayerCoinPanel().revalidate();

    }

    public void setOpponentViews(DeckView deckView, HandView handView, DiscardPileView discardPileView,
                                 BenchView benchView, CoinView coinView) {

        board.getOpponentDeckPanel().add(deckView).revalidate();
        board.getOpponentHandCards().add(handView).revalidate();
        board.getOpponentDiscardPanel().add(discardPileView).revalidate();
        board.getOpponentBenchCards().add(benchView).revalidate();
        board.getOpponentCoinPanel().add(coinView);
        board.getOpponentCoinPanel().revalidate();

    }

    public void setCommand(String command) {

        board.getCommandPanel().removeAll();
        board.getCommandPanel().add(new JLabel(command));

    }

    public Component addCommandCombo(Object[] items) {

       return board.getCommandPanel().add(new JComboBox<Object>(items));

    }

    public void addCommandButton(String btnText, ActionListener listener) {

        JButton button = new JButton(btnText);
        button.addActionListener(listener);
        board.getCommandPanel().add(button);

    }

    public void setPlayerActive(ActivePokemonView pokemonView) {
        board.getPlayerActivePanel().add(pokemonView).revalidate();
    }
}
