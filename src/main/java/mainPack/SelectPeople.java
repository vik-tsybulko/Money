package mainPack;

import mainPack.AddMoney;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by Solush on 15.02.2017.
 */
public class SelectPeople {
    private JFrame frame;
    private JButton okButton;
    private JButton cancelButton;
    private static HashMap<String, Boolean> isParticipant = new HashMap<String, Boolean>();
    public static String[] nameMan;
    public static HashMap<String, Boolean> getIsParticipant(){
        return isParticipant;
    }

    public static String[] getNameMan() {
        return nameMan;
    }

    public SelectPeople(){

    }
    public void start(){
        frame = new JFrame("Select people");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setBounds(50, 50, 200, 300);
        int qridy = 0;
        int qridx = 0;

        for (int i = 0; i < AddMoney.getUserCheckBox().length; i++){
            if (3 == qridx){
                qridx = 0;
                qridy++;
            }
            frame.add(AddMoney.getUserCheckBox()[i], new GridBagConstraints(qridx, qridy, 1, 1, 0.0, 0.9,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(15, 15, 15, 15), 0, 0));
            qridx++;
        }
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isSelected();
                frame.setVisible(false);
            }
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        frame.add(okButton, new GridBagConstraints(0, qridy + 1, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        frame.add(Box.createHorizontalStrut(15));
        frame.add(cancelButton, new GridBagConstraints(1, qridy + 1, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        frame.setVisible(true);
        frame.pack();
    }

    private void isSelected(){
        nameMan = new String[AddMoney.getUserCheckBox().length];
        for (int i = 0; i < AddMoney.getUserCheckBox().length; i++){
            isParticipant.put(AddMoney.getUserCheckBox()[i].getText(), AddMoney.getUserCheckBox()[i].isSelected());
            if (AddMoney.getUserCheckBox()[i].isSelected() == true){
                nameMan[i] = AddMoney.getUserCheckBox()[i].getText();
            }
        }
    }
}

