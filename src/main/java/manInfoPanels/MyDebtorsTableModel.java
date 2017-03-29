package manInfoPanels;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Solush on 14.03.2017.
 */
public class MyDebtorsTableModel extends AbstractTableModel {
    ArrayList<String> columnName = new ArrayList<String>();
    @Override
    public int getRowCount() {
        return 10;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
    @Override
    public String getColumnName(int columnIndex){
        columnName.add(0, "Name");
        columnName.add(1, "Summ");
        columnName.add(2, "Pay for");
        columnName.add(3, "Day");
        return columnName.get(columnIndex);
    }
}
