package views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by mikce_000 on 29-May-2017.
 */
public class ChoiceDialog extends JFrame {

    private JTextField txtP1Name;
    private JTextField txtP2Name;
    private JComboBox<String> cmbP1Deck;
    private JComboBox<String> cmbP2Deck;
    private JButton btnLoad;
    private final Path dirPath = Paths.get("src/main/resources/deck");

    public ChoiceDialog() {

        setName("Choice Dialog");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pokemon TCG Game");
        setSize(new Dimension(350, 250));

        JPanel mainPanel = new JPanel();
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel lblTitle = new JLabel("Choose Players' names and respective deck files");
        JLabel lblP1Name = new JLabel("Player 1 Name: ");
        txtP1Name = new JTextField("player1", 10);
        lblP1Name.setLabelFor(txtP1Name);

        String[] deckFiles = this.findFilesinDir();
        JLabel lblP1Deck = new JLabel("Player 1 Deck: ");
        cmbP1Deck = new JComboBox<>(deckFiles);
        lblP1Deck.setLabelFor(cmbP1Deck);

        JLabel lblP2Name = new JLabel("Player 2 Name: ");
        txtP2Name = new JTextField("player2", 10);
        lblP2Name.setLabelFor(txtP2Name);

        JLabel lblP2Deck = new JLabel("Player 2 Deck: ");
        cmbP2Deck = new JComboBox<>(deckFiles);
        lblP2Deck.setLabelFor(cmbP2Deck);

        btnLoad = new JButton("Load Game");

        JButton btnClose = new JButton("Exit");
        btnClose.addActionListener(e -> System.exit(0));

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblTitle)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lblP1Name)
                                        .addComponent(lblP1Deck)
                                        .addComponent(lblP2Name)
                                        .addComponent(lblP2Deck)
                                        .addComponent(btnLoad))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(txtP1Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbP1Deck, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtP2Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbP2Deck, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnClose))))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblP1Name)
                        .addComponent(txtP1Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblP1Deck)
                        .addComponent(cmbP1Deck, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblP2Name)
                        .addComponent(txtP2Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblP2Deck)
                        .addComponent(cmbP2Deck, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(btnLoad)
                        .addComponent(btnClose))
        );

        add(mainPanel);
        setVisible(true);

    }

    public String getP1Name() {
        return txtP1Name.getText();
    }

    public String getP2Name() {
        return txtP2Name.getText();
    }

    public String getP1DeckFile() {
        return Paths.get(dirPath.toString(), cmbP1Deck.getSelectedItem().toString()).toString();
    }

    public String getP2DeckFile() {
        return Paths.get(dirPath.toString(), cmbP2Deck.getSelectedItem().toString()).toString();
    }

    public JButton getBtnLoad(){
        return btnLoad;
    }

    public String[] findFilesinDir() {

        File deckFilesFolder = new File(dirPath.toString());
        File[] deckFilesList = deckFilesFolder.listFiles();

        ArrayList<String> returnedArray = new ArrayList<>(1);
        try {
            for (File file : deckFilesList) {
                if (file.isFile() && file.getName().contains("deck")) {
                    returnedArray.add(file.getName());
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Cannot find deck files... Exiting");
            System.exit(0);
        }

        return returnedArray.toArray(new String[returnedArray.size()]);

    }

}
