import javafx.scene.control.Tab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        table.setColumnSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    ManInfo manInfo = new ManInfo();
                    manInfo.start(getNameSelected());
                }
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
        });
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setVisible(true);

    }
    private String getNameSelected(){
        String name = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
        return name;
    }
}
