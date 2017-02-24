import javafx.scene.control.Tab;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Solush on 17.02.2017.
 */
public class TablePanel {
    private static JPanel panel = new JPanel();
    private JTable table;
    private JScrollPane scrollPane;
    private String nameParty;
    TableModel tableModel = new TableModel();
    public static void repaintPanel(){
        panel.repaint();
    }

    public static JPanel getPanel() {
        return panel;
    }

    public TablePanel(){
        nameParty = PartyInfo.getNameParty();

    }
    public void start(){
        panel.removeAll();
        tableModel.initDB(nameParty);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setVisible(true);

    }
}
