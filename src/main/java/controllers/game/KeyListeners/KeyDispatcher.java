package controllers.game.KeyListeners;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by mikce_000 on 02-Jun-2017.
 */
public class KeyDispatcher implements KeyEventDispatcher {

    private KeyListener listener;

    public KeyDispatcher(KeyListener keyListener){
        listener = keyListener;
    }

    public void setListener(KeyListener listener) {
        this.listener = listener;
    }

    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getID() == KeyEvent.KEY_PRESSED)
            listener.keyPressed(e);
        return false;
    }
}