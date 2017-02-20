import javafx.scene.control.Tab;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Solush on 17.02.2017.
 */
public class TablePanel implements Runnable {
    private static JPanel panel = new JPanel();
    private JTable table;
    private JScrollPane scrollPane;
    private String nameParty;
    Thread thread;
    TableModel tableModel = new TableModel();
    public static void repaintPanel(){
        panel.repaint();
    }

    public static JPanel getPanel() {
        return panel;
    }

    public TablePanel(){
        nameParty = PartyInfo.getNameParty();
//        thread = new Thread(this, "Table");
//        thread.start();

    }
    public void start(){
        tableModel.initDB(nameParty);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setVisible(true);

    }
    @Override
    public void run() {
//        while (true){
//            try {
//                tableModel.initDB(nameParty);
//                repaint();
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
