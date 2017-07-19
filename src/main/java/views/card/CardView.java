package views.card;

import views.game.GameView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class CardView extends JLayeredPane {

    private JTable infoTable;
    private JButton backSideBtn;

    public CardView() {

        this.setMaximumSize(new Dimension(100, 110));
        this.setPreferredSize(new Dimension(100, 110));
        this.setMinimumSize(new Dimension(100, 110));

        MouseListener descListeners = getMouseListener();
        String[][] cardInfo = new String[][]{{"Name: ", "Pikachu"},
                {"Type: ", "FFI"}};
        String tblHeaders[] = {"Label", "Info"};
        infoTable = new JTable(new DefaultTableModel(cardInfo, tblHeaders) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });
        infoTable.setPreferredScrollableViewportSize(infoTable.getPreferredSize());
        infoTable.setTableHeader(null);
        infoTable.setShowHorizontalLines(false);
        infoTable.setShowVerticalLines(false);
        infoTable.setFont(new Font("Sans-Serif", Font.PLAIN, 9));
        infoTable.addMouseListener(descListeners);
        infoTable.setCellSelectionEnabled(false);
        JScrollPane tblContainer = new JScrollPane(infoTable);
        tblContainer.setBounds(0, 0, 100, 110);
        tblContainer.addMouseListener(descListeners);
        this.add(tblContainer, JLayeredPane.DEFAULT_LAYER);


        backSideBtn = new JButton();
        backSideBtn.setBackground(Color.black);
        backSideBtn.setAlignmentY(24f);
        backSideBtn.setBorderPainted(false);
        backSideBtn.setOpaque(true);
        backSideBtn.setBounds(0, 0, 100, 110);

        ImageIcon coverImg = new ImageIcon(getClass().getResource("/images/icon.png"));
        coverImg.setImage(coverImg.getImage().getScaledInstance(backSideBtn.getWidth(), backSideBtn.getHeight(), java.awt.Image.SCALE_SMOOTH));
        backSideBtn.setIcon(coverImg);

        this.add(backSideBtn, JLayeredPane.PALETTE_LAYER);
    }

    public JButton getBackSideBtn() {
        return backSideBtn;
    }

    public JTable getInfoTable() {
        return infoTable;
    }

    public void setName(String newName) {
        infoTable.getModel().setValueAt(newName, 0, 1);
    }

    public void setType(String newType) {
        infoTable.getModel().setValueAt(newType, 1, 1);
    }

    public void setListeners(KeyListener keyListener) {
        infoTable.addKeyListener(keyListener);
    }

    protected String getCardName() {
        return infoTable.getModel().getValueAt(0, 1).toString();
    }

    public String getCardType() {
        return infoTable.getModel().getValueAt(1, 1).toString();
    }


    public void invalidateKeyListeners(KeyListener keyListener) {
        infoTable.removeKeyListener(keyListener);
    }

    private MouseListener getMouseListener(){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Component component = (Component) e.getSource();
                GameView gameView = (GameView) SwingUtilities.getAncestorOfClass(GameView.class, component);

                JPanel descPanel = gameView.getBoard().getPlayerPokDescPanel();
                descPanel.removeAll();
                descPanel.revalidate();
                descPanel.repaint();
                JTextArea textArea = new JTextArea(getCardDesc());
                textArea.setLineWrap(true);
                textArea.setEditable(false);
                descPanel.add(new JScrollPane(textArea));
                descPanel.revalidate();

                setBorder(BorderFactory.createLineBorder(Color.green));

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    protected abstract String getCardDesc();
}
