package manInfoPanels;

import mainPack.InteractWithDB;
import mainPack.PartyInfo;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Solush on 14.03.2017.
 */
public class MyPaymentTableModel extends AbstractTableModel {

    ArrayList<String> columnName = new ArrayList<String>();
    List<String[]> data = new ArrayList<String[]>();
    InteractWithDB interactWithDB = new InteractWithDB();
    String name;

    MyPaymentTableModel(String name){
        this.name = name;
        data = interactWithDB.getDataForMyPayment(PartyInfo.getNameParty(), name);
    }
    @Override
    public int getRowCount() {
        if (data.isEmpty()) {
            return 1;
        }else return data.size();
    }
    @Override
    public int getColumnCount() {
        if (data.isEmpty()) {
            return 1;
        }else return 4;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (!data.isEmpty()) {
            String[] row = data.get(rowIndex);
            return row[columnIndex];
        } else return "";
    }
    @Override
    public String getColumnName(int columnIndex){
        if (!data.isEmpty()) {
            columnName.add(0, "Day");
            columnName.add(1, "Pay for");
            columnName.add(2, "Summ");
            columnName.add(3, "Participants");
            return columnName.get(columnIndex);
        } else return "No Payments";
    }
}
