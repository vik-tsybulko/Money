package mainPack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by viktor on 06.02.17.
 */
public class InteractWithDB {
    public InteractWithDB(){

    }

    public void addPartyInfoToDB(String nameParty, int qMan, int qDay){
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String queryCreateTable = "CREATE TABLE QUANTITY("
                    + "QMAN INTEGER NOT NULL PRIMARY KEY, "
                    + "QDAY INTEGER NOT NULL"
                    + ")";
            String queryInsertToTable = "INSERT INTO QUANTITY "
                    + "VALUES (" + qMan + ", " + qDay + ")";
            statement.execute(queryCreateTable);
            statement.execute(queryInsertToTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null){
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }
    }
    public void addManInfoToDB(String nameParty, String[] nameMan, int qDay){
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String queryCreateTableUsers = "CREATE TABLE USER("
                    + "ID INTEGER NOT NULL PRIMARY KEY, "
                    + "USERNAME TEXT NOT NULL";
            for (int i = 1; i <= qDay; i++){
                queryCreateTableUsers += ", Day" + i + " INTEGER NOT NULL DEFAULT 0";
            }
            queryCreateTableUsers += ")";
            statement.execute(queryCreateTableUsers);

            for (int i = 0; i < nameMan.length; i++){
                String s = nameMan[i];
                String insertUserTableSQL = "INSERT INTO USER "
                        + "(ID, USERNAME)"
                        + "VALUES (" + (i + 1) + ", " + "'" + s + "'"
                        + ")";
                statement.execute(insertUserTableSQL);
            }
            for (int i = 0; i < nameMan.length; i++) {
                String nameTable = nameMan[i].replaceAll(" ", "_");
                String queryCreateTableUser = "CREATE TABLE " + nameTable
                        + "(Name TEXT NOT NULL PRIMARY KEY";
                for (int j = 1; j <= qDay; j++) {
                    queryCreateTableUser += ", Day" + j + " INTEGER NOT NULL DEFAULT 0";
                }
                queryCreateTableUser += ")";
                statement.execute(queryCreateTableUser);
                for (int j = 0; j < nameMan.length;) {
                    if (nameMan[j] == nameMan[i]){
                        j++;
                        continue;
                    }
                    String insertName = "INSERT INTO " + nameTable
                            + "(Name) VALUES ('" + nameMan[j] + "')";
                    statement.execute(insertName);
                    j++;
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null){
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }
    }
    public void addMoney(String nameParty, String day, String nameMan, int newMoney) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "UPDATE USER SET "
                    + day + "=" + newMoney
                    + " WHERE USERNAME =" + "'" + nameMan + "'";
            statement.execute(query);
            TablePanel.getPanel().repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null){
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

//    public void addMoney(String nameParty, String day, Map<String, Integer> money){
//        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
//        connectionJDBC.init(nameParty);
//        Connection connection = connectionJDBC.getConnection();
//        Statement statement = null;
//        try {
//            statement = connection.createStatement();
//
//            for (Map.Entry entry : money.entrySet()){
//                String query = "UPDATE USER SET "
//                        + day + " = " + Integer.parseInt(entry.getValue().toString())
//                        + " WHERE USERNAME =" + "'" + entry.getKey().toString() + "'";
//                statement.execute(query);
//            }
//            TablePanel.getPanel().repaint();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            if (statement != null){
//                try {
//                    statement.close();
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }
//    }
    }
    public Map<String, Integer> getMoneyInMan(String nameParty, String day){
        Map<String, Integer> money = new HashMap<String, Integer>();
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT USERNAME, " + day + " FROM USER";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                    money.put(resultSet.getString(1), resultSet.getInt(2));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null){
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }
        return money;
    }
    public int getMoney(String nameParty, String day, String nameMan){
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        int money = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT " + day + " FROM USER WHERE USERNAME = '" + nameMan + "'");
            money = resultSet.getInt(1);
        } catch (SQLException e) {

        }finally {
        if (statement != null){
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
        return money;
    }
    public void addPaymentToDB (String nameParty, String suplierName, String[] userName, String day, int summ, String payFor){
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        String nameTable = "pay_" + payFor.replaceAll(" ", "_");
        System.out.println(nameTable);

        try {
            statement = connection.createStatement();
            String query = "CREATE TABLE " + nameTable
                    + " (suplierName TEXT NOT NULL, "
                    + "userName TEXT NOT NULL, "
                    + "day TEXT NOT NULL, "
                    + "summPayment INTEGER NOT NULL, "
                    + "payFor VARCHAR(20) NOT NULL)";
            statement.execute(query);
            for (String s : userName){
                if (s == null) continue;
                query = "INSERT INTO " + nameTable + " VALUES ("
                        + "'" + suplierName + "', "
                        + "'" + s + "', "
                        + "'" + day + "', "
                        + summ + ", "
                        + "'" + payFor + "')";
                statement.execute(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null){
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }
    }
public int payCount = 0;
    public List<String[]> getData (String nameParty){
        List<String> tables = null;
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        List<String[]> data = new ArrayList<String[]>();
        if (tables == null) {
            tables = new ArrayList<String>();
            String query = "select name from sqlite_master where type = 'table'";
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        tables.add(i - 1, resultSet.getString(i));
                    }
                }
                tables.remove("QUANTITY");
                tables.remove("USER");
                payCount = tables.size();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (String s : tables){
            String queryGetData = "SELECT * FROM " + s;
            data.clear();
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(queryGetData);
                while (resultSet.next()){
                    String[] row = new String[resultSet.getMetaData().getColumnCount()];
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
                        row[i - 1] = resultSet.getString(i);
                    }
                    data.add(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if (statement != null){
                    try {
                        resultSet.close();
                        statement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            tables.remove(s);
            break;
        }
        return data;
    }
    public void getDataPayments(){

    }
}
