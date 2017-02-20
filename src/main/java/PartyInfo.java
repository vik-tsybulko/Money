import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by viktor on 07.02.17.
 */
public class PartyInfo {
    PartyInfo(){

    }
    private static JFrame frame = new JFrame("Party Info");
    private JPanel buttonPanel;
    private JButton addMoneyButton;
    private JButton cancelButton;
    private static String nameParty;
    public static String getNameParty() {
        return nameParty;
    }
    public static JFrame getFrame() {
        return frame;
    }

    public void start(){
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBounds(50, 50, 250, 300);

        nameParty = FirsPage.getInstance().getSelectParty();
        TablePanel tablePanel = new TablePanel();
        tablePanel.start();
        frame.add(TablePanel.getPanel(), BorderLayout.CENTER);

        addMoneyButton = new JButton("Add Money");
        addMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMoney addMoney = new AddMoney(nameParty);
                addMoney.start();
            }
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        buttonPanel = new JPanel();
        buttonPanel.add(addMoneyButton);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.pack();
    }


}
