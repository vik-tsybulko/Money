package mainPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by viktor on 06.02.17.
 */
public class InitMan {
    InitMan(){

    }
    private JFrame frame;

    private JLabel[] nameManLabel;
    private JTextField[] nameManTextField;

    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;

    public void start(final String nameParty, int qMan, final int qDay){
        frame = new JFrame("Initialization Your Man");
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setBounds(50, 50, 300, 500);
        frame.setLayout(new GridBagLayout());

        nameManLabel = new JLabel[qMan];
        nameManTextField = new JTextField[qMan];
        for (int i = 0; i < qMan; i++){
            nameManLabel[i] = new JLabel("Enter name Man " + (i + 1));
            frame.add(nameManLabel[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.9,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(15, 15, 15, 15), 0, 0));
        }
        for (int i = 0; i < qMan; i++){
            nameManTextField[i] = new JTextField(10);
            frame.add(nameManTextField[i], new GridBagConstraints(1, i, 1, 1, 0.0, 0.9,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(15, 15, 15, 15), 0, 0));
        }

        buttonPanel = new JPanel(new GridBagLayout());
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                InteractWithDB inWDB = new InteractWithDB();
                inWDB.addManInfoToDB(nameParty, getMenName(nameManTextField), qDay);
                addToListNameParty(nameParty);
                frame.setVisible(false);
            }
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                CreateParty createParty = new CreateParty();
                createParty.start();
            }
        });
        buttonPanel.add(okButton);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, new GridBagConstraints(0, qMan, 2, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));

        frame.setVisible(true);
        frame.pack();

    }
    private String[] getMenName(JTextField[] textFields){
        String[] nameMen = new String[textFields.length];
        for (int i = 0; i < textFields.length; i++){
            nameMen[i] = textFields[i].getText();
        }
        return nameMen;
    }
    private void addToListNameParty(String nameParty){
        FirsPage firsPage = FirsPage.getInstance();
        firsPage.defaultListModel.addElement(nameParty);
        if (firsPage.defaultListModel.contains("No Party")){
            firsPage.defaultListModel.removeElement("No Party");
        }
        firsPage.rePaint();
    }
}
