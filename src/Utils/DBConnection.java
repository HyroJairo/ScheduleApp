package Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.forName;

/**
 * This is the database driver connection class
 */
public class DBConnection {

    // JDBC URL Parts
    private static final String protocol = "jdbc";
    private static final String subProtocol = ":mysql:";
    private static final String subName = "//wgudb.ucertify.com/WJ07gew";

    // JDBC URL
    private static final String jdbcURL = protocol + subProtocol + subName;

    // JDBC driver
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    // Opening up the Connection
    private static Connection con = null;
    private static String username = "U07gew";
    private static String password = "53689021687";

    /**
     * starts the connection
     * @return the connection
     */
    public static Connection startConnection() {
        try {
            Class.forName(driver);
            con = (Connection)DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Success");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + "f");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage() + "e");
        }
        return con;
    }

    /**
     * closes the connection
     * @throws SQLException an sqlException
     */
    public static void closeConnection() throws SQLException {
        try {
            con.close();
            System.out.println("Connection Closed");
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
