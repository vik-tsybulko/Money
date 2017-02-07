import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
                queryCreateTable += ", DAY" + i + " INTEGER NOT NULL";
            }
            queryCreateTable += ")";
            statement.execute(queryCreateTable);

            for (int i = 0; i < nameMan.length; i++){
                String s = nameMan[i];
                String insertUserTableSQL = "INSERT INTO USER "
                        + "VALUES (" + (i + 1) + ", " + "'" + s + "'";
                for (int i1 = 0; i1 < qDay; i1++){
                    insertUserTableSQL += ", 0";
                }
                insertUserTableSQL += ")";
                statement.execute(insertUserTableSQL);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
