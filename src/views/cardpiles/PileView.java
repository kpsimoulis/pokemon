package views.cardpiles;

import javax.swing.*;
import java.awt.*;

public abstract class PileView extends JPanel {

    private int noOfCards;
    private JButton backSideBtn;

    public PileView() {

        this.noOfCards = 0;

        this.setOpaque(false);
        this.setMaximumSize(new Dimension(140, 180));
        this.setPreferredSize(new Dimension(140, 180));

        backSideBtn = new JButton(Integer.toString(noOfCards), new ImageIcon(getClass().getResource("/images/deck_img.png")));
        backSideBtn.setVerticalTextPosition(SwingConstants.CENTER);
        backSideBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        backSideBtn.setAlignmentY(24f);
        backSideBtn.setOpaque(false);
        backSideBtn.setContentAreaFilled(false);
        backSideBtn.setBorderPainted(false);
        backSideBtn.setBounds(0,0,135,180);
        backSideBtn.setBorder(BorderFactory.createEmptyBorder());
        backSideBtn.setFont(new Font("Sans-Serif", Font.BOLD,30));
        backSideBtn.setForeground(Color.black);

        this.add(backSideBtn);

    }

    public void setNoOfCards(int noOfCards){
        this.noOfCards = noOfCards;
        changeBtnText(Integer.toString(noOfCards));
    }

    public void decrementNoOfCards(){
        this.noOfCards--;
        changeBtnText(Integer.toString(noOfCards));
    }

    public void incrementNoOfCards(){
        this.noOfCards++;
        changeBtnText(Integer.toString(noOfCards));
    }

    private void changeBtnText(String newText){
        backSideBtn.setText(newText);
        backSideBtn.repaint();
    }

}
