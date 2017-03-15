package manInfoPanels;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Solush on 14.03.2017.
 */
public class MyPaymentTableModel extends AbstractTableModel {
    ArrayList<String> columnName = new ArrayList<String>();



    MyPaymentTableModel(){

    }
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
        columnName.add(0, "Day");
        columnName.add(1, "Pay for");
        columnName.add(2, "Summ");
        columnName.add(3, "Buhari");
        return columnName.get(columnIndex);
    }
}
