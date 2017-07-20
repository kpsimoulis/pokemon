package comp354;

import controllers.game.GameController;

import javax.swing.*;


public class Main implements Runnable{

    public void run() {
        GameController mainControl = new GameController();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

}
