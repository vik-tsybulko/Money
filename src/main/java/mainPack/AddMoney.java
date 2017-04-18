package mainPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Solush on 08.02.2017.
 */
public class AddMoney {
    private static JFrame frame;
    private JLabel dayLabel;

    public static JComboBox getDayComboBox() {
        return dayComboBox;
    }

    public static JComboBox getSuplierComboBox() {
        return suplierComboBox;
    }

    public static JTextField getAmountTextField() {
        return amountTextField;
    }

    public static JTextField getPayForTextField() {
        return payForTextField;
    }

    private static JComboBox dayComboBox = new JComboBox();
    private JLabel userLabel;
    private JButton userButton;
    private static JCheckBox[] userCheckBox;
    private String[] nameMan;
    private JLabel suplierLabel;
    private static JComboBox suplierComboBox = new JComboBox();
    private JLabel amountLabel;
    private static JTextField amountTextField;
    private JLabel payForLabel;
    private static JTextField payForTextField;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private String nameParty;

    public static JCheckBox[] getUserCheckBox(){
        return userCheckBox;
    }

    public AddMoney(String nameParty){
        this.nameParty = nameParty;

    }

    public void start(){
        frame = new JFrame("Add Money");
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setBounds(50, 50, 200, 300);

        init();
        dayLabel = new JLabel("Select a day");
        frame.add(dayLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        frame.add(dayComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        userLabel = new JLabel("Select a People");
        frame.add(userLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        userButton = new JButton("Select");
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectPeople selectPeople = new SelectPeople();
                selectPeople.start();
            }
        });
        frame.add(userButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        suplierLabel = new JLabel("Select a suplier");
        frame.add(suplierLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        frame.add(suplierComboBox, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        amountLabel = new JLabel("Amount:");
        frame.add(amountLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        amountTextField = new JTextField(4);
        frame.add(amountTextField, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        payForLabel = new JLabel("Pay for:");
        frame.add(payForLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        payForTextField = new JTextField(15);
        frame.add(payForTextField, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));
        buttonPanel = new JPanel(new GridLayout());
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InteractWithDB interactWithDB = new InteractWithDB();
                interactWithDB.addPayForToTableInDB(nameParty, String.valueOf(suplierComboBox.getSelectedItem()), payForTextField.getText());
                Logic logic = new Logic();
                logic.addPay();
                TableModel.initDB(nameParty);
                TablePanel.repaintPanel();
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
        buttonPanel.add(okButton);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(15, 15, 15, 15), 0, 0));

        frame.setVisible(true);
        frame.pack();
    }
    private void init(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            ConnectionJDBC conjdbc = new ConnectionJDBC();
            conjdbc.init(nameParty);
            connection = conjdbc.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT QDAY FROM QUANTITY");
            dayComboBox.removeAllItems();
            int qDay = resultSet.getInt(1);
            for (int i = 1; i <= qDay; i++){
                String s = "Day " + i;
                dayComboBox.addItem(s);
            }
            resultSet = statement.executeQuery("SELECT QMAN FROM QUANTITY");
            int columnCount = resultSet.getInt(1);
            resultSet = statement.executeQuery("SELECT USERNAME FROM USER");
            nameMan = new String[columnCount];
            userCheckBox = new JCheckBox[columnCount];
            int i = 0;
            suplierComboBox.removeAllItems();
            while (resultSet.next()){
                    nameMan[i] = resultSet.getString(1);
                    suplierComboBox.addItem(nameMan[i]);
                    userCheckBox[i] = new JCheckBox(nameMan[i]);
                    i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null){
                try {
                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
