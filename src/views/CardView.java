package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mikce_000 on 26-May-2017.
 */
public class CardView extends JFrame{

    private String[][] cardInfo;

    public CardView(){

        JLayeredPane layeredPane = this.getLayeredPane();
        layeredPane.setMaximumSize(new Dimension(200, 300));

        cardInfo = new String[][]{{"Name: ", "Pikachu"},
                {"Type: ", "FFI"}};
        String tblHeaders[] = {"Label", "Info"};
        JTable infoTable = new JTable(cardInfo, tblHeaders );
        infoTable.setPreferredScrollableViewportSize(infoTable.getPreferredSize());
        infoTable.setTableHeader(null);
        infoTable.setShowHorizontalLines(false);
        infoTable.setShowVerticalLines(false);
        JScrollPane tblContainer = new JScrollPane(infoTable);
        tblContainer.setBounds(0,0,200,50);
        layeredPane.add(tblContainer, JLayeredPane.DEFAULT_LAYER);

        JButton backSide = new JButton();
        backSide.setBackground(Color.black);
        backSide.setAlignmentY(24f);
        backSide.setBorderPainted(false);
        backSide.setOpaque(true);
        backSide.setIcon(new ImageIcon(getClass().getResource("/images/icon.png")));
        backSide.setBounds(0,0,200,200);
        backSide.addActionListener((ActionEvent e) -> backSide.setVisible(false));
        layeredPane.add(backSide, JLayeredPane.PALETTE_LAYER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 300));
        pack();
        setVisible(true);

    }

    public static void main (String [] args){

        CardView view = new CardView();

    }

}
