package views.cardcontainer;

import views.card.CardView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class ContainerView extends JPanel {

    private JScrollPane scrollPane;
    private JPanel panel;
    private JLabel countLbl;
    private ArrayList<CardView> cardViews;

    public ContainerView(int initialCapacity){
        this.setOpaque(false);

        cardViews = new ArrayList<CardView>(initialCapacity);

        this.setPreferredSize(new Dimension(900, 120));

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(900, 120));
        scrollPane.setName("cardScrolls");
        scrollPane.setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        panel = new JPanel();
        panel.setOpaque(false);
        scrollPane.setViewportView(panel);
        scrollPane.getViewport().setOpaque(false);

        countLbl = new JLabel("0");
        countLbl.setFont(new Font("Arial", Font.BOLD, 24));
        countLbl.setForeground(Color.black);
        this.add(countLbl);
        this.add(scrollPane);

    }

    public JPanel getPanel() {
        return panel;
    }

    public ArrayList<CardView> getCardViews(){
        return cardViews;
    }

    public void addCardView(CardView newView){

        cardViews.add(newView);
        panel.add(newView).revalidate();
        countLbl.setText(Integer.toString(Integer.parseInt(countLbl.getText()) + 1));

    }

    public CardView removeCardView(CardView cardViewToRemove) {

        CardView returnedView = null;
        for(CardView view: cardViews){
            if (view == cardViewToRemove ){
                returnedView = view;
                panel.remove(view);
                cardViews.remove(view);
                break;
            }
        }
        countLbl.setText(Integer.toString(Integer.parseInt(countLbl.getText()) - 1));
        return returnedView;

    }

    public int getNoOfCards() {
        return cardViews.size();
    }

}
