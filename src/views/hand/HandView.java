package views.hand;

import views.card.CardView;

import javax.swing.*;
import java.awt.*;

public class HandView extends JScrollPane{

    private JPanel panel;

    public HandView(){

        setPreferredSize(new Dimension(450, 180));
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        panel = new JPanel();
        setViewportView(panel);

    }

    public void addCardView(CardView newView){

        panel.add(newView);

    }

}
