package mainPack;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.*;
import java.util.*;

/**
 * Created by viktor on 06.02.17.
 */
public class InteractWithDB {
    public InteractWithDB() {

    }

    public void addPartyInfoToDB(String nameParty, int qMan, int qDay) {
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
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    public void addManInfoToDB(String nameParty, String[] nameMan, int qDay) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            //создание общей таблицы пользователей
            String queryCreateTableUsers = "CREATE TABLE USER("
                    + "ID INTEGER NOT NULL PRIMARY KEY, "
                    + "USERNAME TEXT NOT NULL";
            for (int i = 1; i <= qDay; i++) {
                queryCreateTableUsers += ", Day" + i + " INTEGER NOT NULL DEFAULT 0";
            }
            queryCreateTableUsers += ")";
            statement.execute(queryCreateTableUsers);
            //добавление пользователей в общую аблицу
            for (int i = 0; i < nameMan.length; i++) {
                String s = nameMan[i];
                String insertUserTableSQL = "INSERT INTO USER "
                        + "(ID, USERNAME)"
                        + "VALUES (" + (i + 1) + ", " + "'" + s + "'"
                        + ")";
                statement.execute(insertUserTableSQL);
            }
            //создание таблиц для каждого пользователя
            for (int i = 0; i < nameMan.length; i++) {
                String nameTable = nameMan[i].replaceAll(" ", "_");
                String queryCreateTableUser = "CREATE TABLE " + nameTable + " (payFor TEXT NOT NULL PRIMARY KEY, ";
                for (int j = 0; j < nameMan.length; ) {
                    if (nameMan[j] == nameMan[i]) {
                        j++;
                        continue;
                    }
                    queryCreateTableUser += nameMan[j].replaceAll(" ", "_") + " INTEGER NOT NULL DEFAULT 0, ";
                    j++;
                }
                queryCreateTableUser += "Day INTEGER NOT NULL DEFAULT 1, " +
                        "Summ INTEGER NOT NULL DEFAULT 0, Participants TEXT NOT NULL DEFAULT '')";
                statement.execute(queryCreateTableUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addMoneyToCommonDB(String nameParty, String day, String nameMan, int newMoney,
                                   String suplierName, int pay, String payFor, int summ) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "UPDATE USER SET "
                    + day + "=" + newMoney
                    + " WHERE USERNAME =" + "'" + nameMan + "'";
            statement.executeUpdate(query);
            TablePanel.getPanel().repaint();
            query = "";
            addMoneyToPayForDB(statement, suplierName, nameMan, pay, payFor, day, summ);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void addMoneyToPayForDB(Statement statement, String suplierName, String nameMan,
                                   int pay, String payFor, String sday, int summ) throws SQLException {
        String nameTable = suplierName.replaceAll(" ", "_");
        String payForName = payFor.replaceAll(" ", "_");
        int day = Integer.valueOf(sday.replaceAll("Day", ""));
        statement.executeUpdate("UPDATE " + nameTable
                + " SET Day = " + day
                + " WHERE payFor = '" + payForName + "'");
        statement.executeUpdate("UPDATE " + nameTable
                + " SET Summ = " + summ
                + " WHERE payFor = '" + payForName + "'");
        nameMan = nameMan.replaceAll(" ", "_");
        ResultSet resultSet = statement.executeQuery("SELECT Participants FROM "
                + nameTable + " WHERE payFor = '" + payForName + "'");
        String oldName = resultSet.getString(1);
        String newName;
        if (nameMan != suplierName) {
            statement.executeUpdate("UPDATE " + nameTable
                    + " SET " + nameMan + " = " + pay
                    + " WHERE payFor = '" + payForName + "'");
            if (oldName.isEmpty()) {
                newName = nameMan;
            } else {
                newName = oldName + ", " + nameMan;
            }
            statement.executeUpdate("UPDATE " + nameTable
                    + " SET Participants = '" + newName + "' WHERE payFor = '" + payForName + "'");
            resultSet.close();
        } else if (nameMan == suplierName){
            if (oldName.isEmpty()) {
                newName = nameMan;
            } else {
                newName = oldName + ", " + nameMan;
            }
            statement.executeUpdate("UPDATE " + nameTable
                    + " SET Participants = '" + newName + "' WHERE payFor = '" + payForName + "'");
            resultSet.close();
        }
        statement.close();
    }

    public int getMoney(String nameParty, String day, String nameMan) {
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

        } finally {
            if (statement != null) {
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

    //добавляет таблицу закидона
    public void addPaymentToDB(String nameParty, String suplierName, String[] userName,
                               String day, int summ, String payFor) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        String nameTable = "pay_" + payFor.replaceAll(" ", "_");
        try {
            statement = connection.createStatement();
            String query = "CREATE TABLE " + nameTable
                    + " (suplierName TEXT NOT NULL, "
                    + "userName TEXT NOT NULL, "
                    + "day TEXT NOT NULL, "
                    + "summPayment INTEGER NOT NULL, "
                    + "payFor VARCHAR(20) NOT NULL)";
            statement.execute(query);
            for (String s : userName) {
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
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }
    }
    public List<String[]> getDataForMyPayment(String nameParty, String name) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        List<String[]> data = new ArrayList<String[]>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT Day, payFor, Summ, Participants FROM "
                    + name.replaceAll(" ", "_"));

            while (resultSet.next()) {
                String[] row = new String[resultSet.getMetaData().getColumnCount()];
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row[i - 1] = resultSet.getString(i);
                    System.out.print(row[i - 1] + " ");
                }
                System.out.println("");
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null) {
                try {
                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return data;
    }
    public void addPayForToTableInDB(String nameParty, String nameSuplier, String payFor){
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        String query = "INSERT INTO " + nameSuplier.replaceAll(" ", "_")
                + "(payFor) VALUES (" + "'" + payFor.replaceAll(" ", "_") + "')";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
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
}
