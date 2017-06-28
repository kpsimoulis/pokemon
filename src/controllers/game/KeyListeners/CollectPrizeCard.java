package controllers.game.KeyListeners;

import controllers.card.CardController;
import controllers.game.GameController;
import javafx.util.Pair;
import views.card.CardView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by mikce_000 on 11-Jun-2017.
 */
public class CollectPrizeCard extends AbstractGameListener implements KeyListener {

    public CollectPrizeCard(GameController controller) {
        super(controller);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
            case KeyEvent.VK_NUMPAD1: {
                choosePrizeCard(0);
                break;
            }
            case KeyEvent.VK_2:
            case KeyEvent.VK_NUMPAD2: {
                choosePrizeCard(1);
                break;
            }
            case KeyEvent.VK_3:
            case KeyEvent.VK_NUMPAD3: {
                choosePrizeCard(2);
                break;
            }
            case KeyEvent.VK_4:
            case KeyEvent.VK_NUMPAD4: {
                choosePrizeCard(3);
                break;
            }
            case KeyEvent.VK_5:
            case KeyEvent.VK_NUMPAD5: {
                choosePrizeCard(4);
                break;
            }
            case KeyEvent.VK_6:
            case KeyEvent.VK_NUMPAD6: {
                choosePrizeCard(5);
                break;
            }
            default: {
                System.out.println("Incorrect Key");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void choosePrizeCard(int index) {

        try{
            Pair<CardController, CardView> pair = controller.getHumanController().getPrizeCardController().chooseCard(index);
            if (pair != null) {
                controller.getHumanController().getHandController().addCard(pair);
                pair.getKey().returnBackCover();
                controller.getView().disableKeyListener();
                controller.aiActiveDefeated();
            }
        }catch (IndexOutOfBoundsException ignored){
        }

    }

}
