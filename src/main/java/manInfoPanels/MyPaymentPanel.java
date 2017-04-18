package manInfoPanels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Solush on 14.03.2017.
 */
public class MyPaymentPanel extends JPanel {
    private JLabel myPaymentLabel = new JLabel("My Payments: ");
    private JTable myPaymentTable;
    private JScrollPane scrollPane;
    private Box labelBox = Box.createHorizontalBox();
    private Box tableBox = Box.createHorizontalBox();
    private Box mainBox = Box.createVerticalBox();
    String nameSelected;

    public MyPaymentPanel(String name){
        nameSelected = name;
    }

    public void start() {
        removeAll();
        labelBox.add(Box.createHorizontalGlue());
        labelBox.add(myPaymentLabel);
        labelBox.add(Box.createHorizontalGlue());

        MyPaymentTableModel tableModel = new MyPaymentTableModel(nameSelected);
        myPaymentTable = new JTable(tableModel);

        myPaymentTable.getTableHeader().setReorderingAllowed(false);
        myPaymentTable.getTableHeader().setResizingAllowed(false);
        myPaymentTable.setColumnSelectionAllowed(true);

        scrollPane = new JScrollPane(myPaymentTable);
        scrollPane.setPreferredSize(new Dimension(450, 70));
        tableBox.add(scrollPane);
        mainBox.add(labelBox);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(tableBox);
        add(mainBox);
        setVisible(true);
    }
}
