package work_demo.DAO;
import java.sql.*;

public class DBUtils {
    static private final String url = "jdbc:mysql://localhost:3306/steam";
    static private final String username = "root";
    static private final String password = "zx220904";
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


    /*
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // 1. 创建Statement
            Statement stmt = conn.createStatement();
            // 2. 执行查询
            ResultSet rs = stmt.executeQuery("SELECT * FROM admins");
            // 3. 处理结果集
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name"));
            }

            // 插入数据示例
            String insertSQL = "INSERT INTO admins (id,name,password) VALUES (?, ?,?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, "001");
            pstmt.setString(2, "zx");
            pstmt.setString(3, "zx");
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */
}
