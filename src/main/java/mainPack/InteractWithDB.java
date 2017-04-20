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
//создание вспомагательной таблицы с информацией о пати
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
//создание общей таблицы и таблицы для "MyPayments"
    public void addManInfoToDB(String nameParty, String[] nameMan, int qDay) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        try {
            //создание общей таблицы пользователей
            statement = connection.createStatement();
            String queryCreateTableUsers = "CREATE TABLE USER(USERNAME TEXT NOT NULL PRIMARY KEY";
            for (String s : nameMan) {
                queryCreateTableUsers += ", " + s + " INTEGER NOT NULL DEFAULT 0";
            }
            queryCreateTableUsers += ", Summ INTEGER NOT NULL DEFAULT 0)";
            statement.executeUpdate(queryCreateTableUsers);
            //добавление пользователей в общую nаблицу и создание таблицы для каждого пользователя
            for (String s : nameMan) {
                statement.executeUpdate("INSERT INTO USER (USERNAME) VALUES ('" + s + "')");
                String nameTable = s.replaceAll(" ", "_");
                String queryCreateTableUser = "CREATE TABLE " + nameTable + " (payFor TEXT NOT NULL PRIMARY KEY, ";
                for (int j = 0; j < nameMan.length; ) {
                    if (nameMan[j] == s) {
                        j++;
                        continue;
                    }
                    queryCreateTableUser += nameMan[j].replaceAll(" ", "_") + " INTEGER NOT NULL DEFAULT 0, ";
                    j++;
                }
                queryCreateTableUser += "Suplier TEXT NOT NULL DEFAULT '', Day INTEGER NOT NULL DEFAULT 1, " +
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
//метод добавляющий информацию в общую таблицу БД из которой составляется таблица для "PartyInfo"
    public void addMoneyToCommonDB(String nameParty, String day, String nameMan,
                                   String suplierName, int pay, String payFor, int summ) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            //доделать подсчет и добавление суммы
            statement = connection.createStatement();
            String query = "SELECT " + suplierName + " FROM USER WHERE USERNAME = '" + nameMan + "'";
            resultSet = statement.executeQuery(query);
            ps = connection.prepareStatement("UPDATE USER SET " + suplierName + " = ? + ? WHERE USERNAME = ?");
            if (nameMan != suplierName) {
                int oldMoney = resultSet.getInt(1);
                ps.setInt(1, pay);
                ps.setInt(2, oldMoney);
                ps.setString(3, nameMan);
                ps.executeUpdate();
                ps = connection.prepareStatement("UPDATE USER SET " + nameMan + " = ? WHERE USERNAME = ?");
                query = "SELECT " + suplierName + " FROM USER WHERE USERNAME = '" + nameMan + "'";
                resultSet = statement.executeQuery(query);
                int money = - resultSet.getInt(1);
                System.out.println(money);
                ps.setInt(1, money);
                ps.setString(2, suplierName);
                ps.executeUpdate();
            }
            TablePanel.getPanel().repaint();
            addMoneyToPayForDB(statement, suplierName, nameMan, pay, payFor, day, summ);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    resultSet.close();
                    statement.close();
                    ps.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
//метод добавления информации в таблицу БД плательщика
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
        statement.executeUpdate("UPDATE " + nameTable
                + " SET Suplier = '" + suplierName + "'"
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
    //метод возвращающий данные для таблицы "MyPayments"
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
            System.out.println("MyPayments");
            while (resultSet.next()) {
                String[] row = new String[resultSet.getMetaData().getColumnCount()];
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row[i - 1] = resultSet.getString(i);
                    System.out.print(row[i - 1] + " ");
                }
                System.out.println("");
                data.add(row);
            }
        }catch (SQLException e) {
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
//метод возвращающий данные для таблицы "MyDebts"
    public List<String[]> getDataForMyDebts(String nameParty, String name) {
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> nameTable = new ArrayList<String>();
        List<String[]> data = new ArrayList<String[]>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table'");
            while (resultSet.next()) {
                nameTable.add(resultSet.getString(1));
            }
            nameTable.remove("QUANTITY");
            nameTable.remove("USER");
            String query = "";

            for (int i = 0; i < nameTable.size();) {
                if (name.equals(nameTable.get(nameTable.size() - 1))) {
                    if (name.equals(nameTable.get(i))) {
                        i++;
                        continue;
                    }
                    if (i == nameTable.size() - 2) {
                        query += "SELECT Suplier, " + name + ", payFor, Day "
                                + "FROM " + nameTable.get(i) + " WHERE " + name + " != 0";
                        i++;
                    } else {
                        query += "SELECT Suplier," + name + ", payFor, Day "
                                + "FROM " + nameTable.get(i) + " WHERE " + name + " != 0 UNION ALL ";
                        i++;
                    }
                } else {
                    if (name.equals(nameTable.get(i))) {
                        i++;
                        continue;
                    }
                    if (i == nameTable.size() - 1) {
                        query += "SELECT Suplier, " + name + ", payFor, Day "
                                + "FROM " + nameTable.get(i) + " WHERE " + name + " != 0";
                        i++;
                    } else {
                        query += "SELECT Suplier," + name + ", payFor, Day "
                                + "FROM " + nameTable.get(i) + " WHERE " + name + " != 0 UNION ALL ";
                        i++;
                    }
                }
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            System.out.println("MyDebts");
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
    public List<String[]> getDataForMyDebtors(String nameParty, String name){
        ConnectionJDBC connectionJDBC = new ConnectionJDBC();
        connectionJDBC.init(nameParty);
        Connection connection = connectionJDBC.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> nameTable = new ArrayList<String>();
        List<String[]> data = new ArrayList<String[]>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table'");
            while (resultSet.next()) {
                nameTable.add(resultSet.getString(1));
            }
            nameTable.remove("QUANTITY");
            nameTable.remove("USER");
            String query = "";

            for (int i = 0; i < nameTable.size();) {
//
                if (name.equals(nameTable.get(nameTable.size() - 1))) {
                    if (name.equals(nameTable.get(i))) {
                        i++;
                        continue;
                    }
                    if (i == nameTable.size() - 2) {
                        query += "SELECT '" + nameTable.get(i) + "' AS Participants, " + nameTable.get(i) + ", payFor, Day "
                                + "FROM " + name + " WHERE " + nameTable.get(i) + " != 0";
                        i++;
                    } else {
                        query += "SELECT '" + nameTable.get(i) + "' AS Participants," + nameTable.get(i) + ", payFor, Day "
                                + "FROM " + name + " WHERE " + nameTable.get(i) + " != 0 UNION ALL ";
                        i++;
                    }
                } else {
                    if (name.equals(nameTable.get(i))) {
                        i++;
                        continue;
                    }
                    if (i == nameTable.size() - 1) {
                        query += "SELECT '" + nameTable.get(i) + "' AS Participants, " + nameTable.get(i) + ", payFor, Day "
                                + "FROM " + name + " WHERE " + nameTable.get(i) + " != 0";
                        i++;
                    } else {
                        query += "SELECT '" + nameTable.get(i) + "' AS Participants," + nameTable.get(i) + ", payFor, Day "
                                + "FROM " + name + " WHERE " + nameTable.get(i) + " != 0 UNION ALL ";
                        i++;
                    }
                }
//                if (name.equals(nameTable.get(i))) {
//                    i++;
//                    continue;
//                }
//                if (i == nameTable.size() - 1) {
//                    query += "SELECT '" + nameTable.get(i) + "' AS Participants, " + nameTable.get(i) + ", payFor, Day "
//                            + "FROM " + name + " WHERE " + nameTable.get(i) + " != 0";
//                    i++;
//                } else {
//                    query += "SELECT '" + nameTable.get(i) + "' AS Participants," + nameTable.get(i) + ", payFor, Day "
//                            + "FROM " + name + " WHERE " + nameTable.get(i) + " != 0 UNION ALL ";
//                    i++;
//                }
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            System.out.println("MyDebtors");
            while (resultSet.next()) {
                String[] row = new String[resultSet.getMetaData().getColumnCount()];
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row[i - 1] = resultSet.getString(i);
                    System.out.print(row[i - 1] + " ");
                }
                System.out.println(resultSet.isClosed());
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
    //метод добаляющий закидон в таблицу БД тому кто платил
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
