package manInfoPanels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Solush on 14.03.2017.
 */
public class MyDebtsPanel extends JPanel {
    private JLabel myDebtsLabel = new JLabel("My Debts: ");
    private JTable myDebtsTable;
    private JScrollPane scrollPane;
    private Box labelBox = Box.createHorizontalBox();
    private Box tableBox = Box.createHorizontalBox();
    private Box mainBox = Box.createVerticalBox();
    String selectedName;
    public MyDebtsPanel(String name){
        selectedName = name;
    }

    public void start() {
        setBounds(0, 0, 200, 100);
        removeAll();
        labelBox.add(Box.createHorizontalGlue());
        labelBox.add(myDebtsLabel);
        labelBox.add(Box.createHorizontalGlue());

        MyDebtsTableModel tableModel = new MyDebtsTableModel(selectedName);
        myDebtsTable = new JTable(tableModel);
        scrollPane = new JScrollPane(myDebtsTable);
        myDebtsTable.setColumnSelectionAllowed(true);
        myDebtsTable.getTableHeader().setReorderingAllowed(false);
        myDebtsTable.getTableHeader().setResizingAllowed(false);
        scrollPane.setPreferredSize(new Dimension(450, 70));
        tableBox.add(scrollPane);
        mainBox.add(labelBox);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(tableBox);
        add(mainBox);
        setVisible(true);
    }
}
