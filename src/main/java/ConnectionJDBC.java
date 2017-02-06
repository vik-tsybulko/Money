import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by viktor on 06.02.17.
 */
public class ConnectionJDBC {
    ConnectionJDBC(){

    }

    private Connection connection;
    Connection getConnection(){
        return connection;
    }

    public void init(String nameParty){
        String URL = "src/main/resources/" + nameParty + ".sqlite";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + URL);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
