package views.card;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class CardView extends JLayeredPane{

    private JTable infoTable;
    private JButton backSideBtn;

    public CardView(){

        this.setMaximumSize(new Dimension(140, 95));
        this.setPreferredSize(new Dimension(140, 95));

        String[][] cardInfo = new String[][]{{"Name: ", "Pikachu"},
                {"Type: ", "FFI"}};
        String tblHeaders[] = {"Label", "Info"};
        infoTable = new JTable( new DefaultTableModel(cardInfo, tblHeaders){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        });
        infoTable.setPreferredScrollableViewportSize(infoTable.getPreferredSize());
        infoTable.setTableHeader(null);
        infoTable.setShowHorizontalLines(false);
        infoTable.setShowVerticalLines(false);
        infoTable.setFont(new Font("Sans-Serif", Font.PLAIN, 9));
        JScrollPane tblContainer = new JScrollPane(infoTable);
        tblContainer.setBounds(0,0,135,90);
        this.add(tblContainer, JLayeredPane.DEFAULT_LAYER);

        backSideBtn = new JButton();
        backSideBtn.setBackground(Color.black);
        backSideBtn.setAlignmentY(24f);
        backSideBtn.setBorderPainted(false);
        backSideBtn.setOpaque(true);
        backSideBtn.setBounds(0,0,135,90);
        backSideBtn.setBorder(BorderFactory.createEmptyBorder());

        ImageIcon coverImg = new ImageIcon(getClass().getResource("/images/icon.png"));
        coverImg.setImage(coverImg.getImage().getScaledInstance(backSideBtn.getWidth(), backSideBtn.getHeight(),  java.awt.Image.SCALE_SMOOTH));
        backSideBtn.setIcon(coverImg);

        this.add(backSideBtn, JLayeredPane.PALETTE_LAYER);

    }

    public JButton getBackSideBtn(){
        return backSideBtn;
    }

    public JTable getInfoTable(){return infoTable;}

    public void setName(String newName){
        infoTable.getModel().setValueAt(newName, 0, 1);
    }

    public void setType(String newType){
        infoTable.getModel().setValueAt(newType, 1, 1);
    }


}
