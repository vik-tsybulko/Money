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
    private JFrame frame;
    private JScrollPane scrollPane;
    private JTable table;
    private JPanel buttonPanel;
    private JButton addMoneyButton;
    private JButton cancelButton;

    public void start(){
        frame = new JFrame("Party Info");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setBounds(50, 50, 250, 300);


        String nameParty = FirsPage.getInstance().getSelectParty();
        MyTableModel tableModel = new MyTableModel();
        tableModel.initDB(nameParty);

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        addMoneyButton = new JButton("Add Money");
        addMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
    private class MyTableModel extends AbstractTableModel{
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int columnCount;
        int rowCount;
        ArrayList<String> columnName = new ArrayList<String>();
        ArrayList<String[]> dataArray = new ArrayList<String[]>();
        MyTableModel(){

        }

        void initDB(String nameParty){
            try {
                ConnectionJDBC conjdbc = new ConnectionJDBC();
                conjdbc.init(nameParty);
                connection = conjdbc.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT QMAN FROM QUANTITY");
                rowCount = resultSet.getInt(1);

                resultSet = statement.executeQuery("SELECT * FROM USER");
                columnCount = resultSet.getMetaData().getColumnCount();
                while (resultSet.next()){
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++){
                        row[i - 1] = resultSet.getString(i);
                    }
                    dataArray.add(row);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getRowCount() {
            return rowCount;
        }

        @Override
        public int getColumnCount() {
            return columnCount;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String[] rows = dataArray.get(rowIndex);
            return rows[columnIndex];
        }
        @Override
        public String getColumnName(int columnIndex){
            columnName.add(0, "ID");
            columnName.add(1, "Name");
            for (int i = 1; i <= columnCount; i++){
                String s = "Day " + i;
                columnName.add((i + 1), s);
            }
            return columnName.get(columnIndex);
        }

    }

}
