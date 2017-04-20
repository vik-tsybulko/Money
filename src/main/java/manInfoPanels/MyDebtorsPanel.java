package manInfoPanels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Solush on 14.03.2017.
 */
public class MyDebtorsPanel extends JPanel {
    private JLabel myDebtorstLabel = new JLabel("My Debtors: ");
    private JTable myDebtorsTable;
    private JScrollPane scrollPane;
    private Box labelBox = Box.createHorizontalBox();
    private Box tableBox = Box.createHorizontalBox();
    private Box mainBox = Box.createVerticalBox();
    String name;
    public MyDebtorsPanel(String name){
        this.name = name;
    }

    public void start() {
        removeAll();
        labelBox.add(Box.createHorizontalGlue());
        labelBox.add(myDebtorstLabel);
        labelBox.add(Box.createHorizontalGlue());

        MyDebtorsTableModel tableModel = new MyDebtorsTableModel(name);
        myDebtorsTable = new JTable(tableModel);
        myDebtorsTable.setColumnSelectionAllowed(true);
        myDebtorsTable.getTableHeader().setReorderingAllowed(false);
        myDebtorsTable.getTableHeader().setResizingAllowed(false);
        scrollPane = new JScrollPane(myDebtorsTable);
        scrollPane.setPreferredSize(new Dimension(450, 70));
        tableBox.add(scrollPane);
        mainBox.add(labelBox);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(tableBox);
        add(mainBox);
        setVisible(true);
    }
}
