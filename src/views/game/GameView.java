package views.game;

import board.Board;
import views.cardcontainer.BenchView;
import views.cardcontainer.HandView;
import views.cardpiles.DeckView;
import views.cardpiles.DiscardPileView;
import views.coin.CoinView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mikce_000 on 29-May-2017.
 */
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
}
