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




    MyPaymentTableModel(){
        data = interactWithDB.getData(PartyInfo.getNameParty());
    }
    @Override
    public int getRowCount() {
        return interactWithDB.payCount;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rows[2];
            case 1:
                return rows[4];
            case 2:
                return rows[3];
            case 3:
                return rows[1];
        }
        return rows[columnIndex];
    }
    @Override
    public String getColumnName(int columnIndex){
        columnName.add(0, "Day");
        columnName.add(1, "Pay for");
        columnName.add(2, "Summ");
        columnName.add(3, "Suplier");
        columnName.add(4, "Name");
        return columnName.get(columnIndex);
    }
}
