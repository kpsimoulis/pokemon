package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CardView extends JLayeredPane{

    private JTable infoTable;
    private JButton backSide;

    public CardView(){

        this.setMaximumSize(new Dimension(140, 150));

        String[][] cardInfo = new String[][]{{"Name: ", "Pikachu"},
                {"Type: ", "FFI"}};
        String tblHeaders[] = {"Label", "Info"};
        infoTable = new JTable(cardInfo, tblHeaders );
        infoTable.setPreferredScrollableViewportSize(infoTable.getPreferredSize());
        infoTable.setTableHeader(null);
        infoTable.setShowHorizontalLines(false);
        infoTable.setShowVerticalLines(false);
        JScrollPane tblContainer = new JScrollPane(infoTable);
        tblContainer.setBounds(0,0,135,145);
        this.add(tblContainer, JLayeredPane.DEFAULT_LAYER);

        backSide = new JButton();
        backSide.setBackground(Color.black);
        backSide.setAlignmentY(24f);
        backSide.setBorderPainted(false);
        backSide.setOpaque(true);
        backSide.setBounds(0,0,135,145);
        backSide.setBorder(BorderFactory.createEmptyBorder());
        backSide.addActionListener((ActionEvent e) -> backSide.setVisible(false));

        ImageIcon coverImg = new ImageIcon(getClass().getResource("/images/icon.png"));
        coverImg.setImage(coverImg.getImage().getScaledInstance(backSide.getWidth(), backSide.getHeight(),  java.awt.Image.SCALE_SMOOTH));
        backSide.setIcon(coverImg);

        this.add(backSide, JLayeredPane.PALETTE_LAYER);

    }

    public void setName(String newName){
        infoTable.getModel().setValueAt(newName, 0, 1);
    }

    public void setType(String newType){
        infoTable.getModel().setValueAt(newType, 1, 1);
    }

    public static void main (String [] args){

        CardView view = new CardView();
        JFrame newFrame = new JFrame();
        newFrame.setLayeredPane(view);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setMinimumSize(new Dimension(150, 185));
        newFrame.pack();
        newFrame.setVisible(true);

    }

}
