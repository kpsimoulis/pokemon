package views.coin;

import javax.swing.*;
import java.awt.*;

public class CoinView extends JPanel{

    private JButton coinImg;

    public CoinView(){

        this.setMaximumSize(new Dimension(70, 70));
        coinImg = new JButton();
        coinImg.setAutoscrolls(false);
        coinImg.setBorderPainted(false);
        coinImg.setInheritsPopupMenu(true);
        coinImg.setBounds(0,0,70,70);
        coinImg.setBorder(BorderFactory.createEmptyBorder());
        coinImg.setOpaque(false);
        coinImg.setContentAreaFilled(false);
        coinImg.setBorderPainted(false);

        ImageIcon coverImg = new ImageIcon(getClass().getResource("/images/pCoin.png"));
        coverImg.setImage(coverImg.getImage().getScaledInstance(coinImg.getWidth(), coinImg.getHeight(),  java.awt.Image.SCALE_SMOOTH));
        coinImg.setIcon(coverImg);

        add(coinImg);
    }

    public JButton getCoinImg(){
        return coinImg;
    }

    public void showHead(){
        ImageIcon coverImg = new ImageIcon(getClass().getResource("/images/oCoin.png"));
        coverImg.setImage(coverImg.getImage().getScaledInstance(coinImg.getWidth(), coinImg.getHeight(),  java.awt.Image.SCALE_SMOOTH));
        coinImg.setIcon(coverImg);
    }

    public void showTail(){
        ImageIcon coverImg = new ImageIcon(getClass().getResource("/images/pCoin.png"));
        coverImg.setImage(coverImg.getImage().getScaledInstance(coinImg.getWidth(), coinImg.getHeight(),  java.awt.Image.SCALE_SMOOTH));
        coinImg.setIcon(coverImg);
    }

}
