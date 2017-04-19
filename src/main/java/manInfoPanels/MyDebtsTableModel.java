package manInfoPanels;

import mainPack.InteractWithDB;
import mainPack.PartyInfo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solush on 14.03.2017.
 */
public class MyDebtsTableModel extends AbstractTableModel {
    ArrayList<String> columnName = new ArrayList<String>();
    List<String[]> data = new ArrayList<String[]>();
    MyDebtsTableModel(String name){
        InteractWithDB interactWithDB = new InteractWithDB();
        data = interactWithDB.getDataForMyDebts(PartyInfo.getNameParty(), name);
    }
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = data.get(rowIndex);
        return row[columnIndex];
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
