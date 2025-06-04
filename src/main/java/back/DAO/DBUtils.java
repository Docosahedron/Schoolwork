package back.DAO;
import java.sql.*;

public class DBUtils {
    static private final String url = "jdbc:mysql://localhost:3306/steam";
    static private final String username = "root";
    static private final String password = "280101123a";
    //建立连接;;;
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
