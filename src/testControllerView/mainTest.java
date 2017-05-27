package testControllerView;

import card.Energy;
import card.Pokemon;
import cardcontainer.Hand;
import controllers.card.CardController;
import controllers.card.PokemonController;
import controllers.coin.CoinController;
import controllers.hand.HandController;
import player.Coin;
import views.card.PokemonView;
import views.coin.CoinView;
import views.hand.HandView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;


public class mainTest {

    public static void main (String [] args){

//        CoinView view = new CoinView();
//        Coin coin = new Coin();
//        CoinController control = new CoinController(coin, view);
//

        Hand hand = new Hand();
        HandView view = new HandView();
        HandController controller = new HandController(hand, view);
        JFrame newFrame = new JFrame();
        newFrame.setLayout(new FlowLayout());

        Energy e1 = new Energy("Electric", 1,"elec");
        Energy e2 = new Energy("Water", 2,"water");
        Energy e3 = new Energy("Water", 2,"water");
        Energy e4 = new Energy("Water", 2,"water");
        controller.addCard(e1);
        controller.addCard(e2);
        controller.addCard(e3);
        controller.addCard(e4);

        newFrame.add(view);

//
//        JButton button = new JButton("Press to Flip");
//        button.setMaximumSize(new Dimension(100,100));
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                control.flipCoin();
//            }
//        });

        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setMinimumSize(new Dimension(150, 185));
        newFrame.pack();
        newFrame.setVisible(true);

    }

}
