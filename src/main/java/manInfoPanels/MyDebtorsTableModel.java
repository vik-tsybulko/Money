package manInfoPanels;

import mainPack.InteractWithDB;
import mainPack.PartyInfo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solush on 14.03.2017.
 */
public class MyDebtorsTableModel extends AbstractTableModel {
    String name;
    List<String[]> data = new ArrayList<String[]>();
    MyDebtorsTableModel(String name){
        this.name = name;
        InteractWithDB interactWithDB = new InteractWithDB();
        data = interactWithDB.getDataForMyDebtors(PartyInfo.getNameParty(), name);
    }
    ArrayList<String> columnName = new ArrayList<String>();
    @Override
    public int getRowCount() {
        if (data.isEmpty()) {
            return 1;
        } else return data.size();
    }

    @Override
    public int getColumnCount() {
        if (data.isEmpty()) {
            return 1;
        }else return 4;

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        System.out.println(data.isEmpty());
        if (!data.isEmpty()) {
            String[] row = data.get(rowIndex);
            return row[columnIndex];
        } else return "";
    }
    @Override
    public String getColumnName(int columnIndex){
        if (!data.isEmpty()) {
            columnName.add(0, "Name");
            columnName.add(1, "Summ");
            columnName.add(2, "Pay for");
            columnName.add(3, "Day");
            return columnName.get(columnIndex);
        } else return "No Debtors";

    }
}
