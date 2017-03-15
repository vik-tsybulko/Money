import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by viktor on 06.02.17.
 */
public class InteractWithDB {
    InteractWithDB(){

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
            String queryCreateTable = "CREATE TABLE USER("
                    + "ID INTEGER NOT NULL PRIMARY KEY, "
                    + "USERNAME TEXT NOT NULL";
            for (int i = 1; i <= qDay; i++){
                queryCreateTable += ", Day" + i + " INTEGER NOT NULL DEFAULT 0";
            }
            queryCreateTable += ")";
            statement.execute(queryCreateTable);

            for (int i = 0; i < nameMan.length; i++){
                String s = nameMan[i];
                String insertUserTableSQL = "INSERT INTO USER "
                        + "(ID, USERNAME)"
                        + "VALUES (" + (i + 1) + ", " + "'" + s + "'"
                        + ")";
                statement.execute(insertUserTableSQL);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addMoney(String nameParty, String day, Map<String, Integer> money){
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();

            for (Map.Entry entry : money.entrySet()){
                String query = "UPDATE USER SET "
                        + day + " = " + Integer.parseInt(entry.getValue().toString())
                        + " WHERE USERNAME =" + "'" + entry.getKey().toString() + "'";
                statement.execute(query);
            }
            TablePanel.getPanel().repaint();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        }
        return money;
    }
    public void addPaymentToDB (String nameParty, String suplierName, String[] userName, String day, int summ, String payFor){
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        String nameTable = payFor.replaceAll(" ", "_");
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
        }
    }
}
