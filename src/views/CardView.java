package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CardView extends JLayeredPane{

    private JTable infoTable;
    private JButton backSide;

    public CardView(){

        this.setMaximumSize(new Dimension(200, 300));

        String[][] cardInfo = new String[][]{{"Name: ", "Pikachu"},
                {"Type: ", "FFI"}};
        String tblHeaders[] = {"Label", "Info"};
        infoTable = new JTable(cardInfo, tblHeaders );
        infoTable.setPreferredScrollableViewportSize(infoTable.getPreferredSize());
        infoTable.setTableHeader(null);
        infoTable.setShowHorizontalLines(false);
        infoTable.setShowVerticalLines(false);
        JScrollPane tblContainer = new JScrollPane(infoTable);
        tblContainer.setBounds(0,0,200,50);
        this.add(tblContainer, JLayeredPane.DEFAULT_LAYER);

        backSide = new JButton();
        backSide.setBackground(Color.black);
        backSide.setAlignmentY(24f);
        backSide.setBorderPainted(false);
        backSide.setOpaque(true);
        backSide.setIcon(new ImageIcon(getClass().getResource("/images/icon.png")));
        backSide.setBounds(0,0,200,200);
        backSide.addActionListener((ActionEvent e) -> backSide.setVisible(false));
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
        newFrame.setMinimumSize(new Dimension(200, 300));
        newFrame.pack();
        newFrame.setVisible(true);

    }

}
