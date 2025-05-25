package work_demo.SERVICE;
import work_demo.DAO.*;
import work_demo.ENTITY.*;
import java.sql.*;
import java.util.*;

public class UserSer implements UserDao {
    @Override
    public boolean add(User user) {
        String sql = "INSERT INTO users (id,name, password) VALUES (?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            // 执行插入，返回受影响的行数
            int affectedRows = pstmt.executeUpdate();
            // 如果只需要知道是否成功
            return affectedRows > 0;
        /* 如果需要获取生成的ID
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) {
                int id = rs.getInt(1); // 获取自增ID
                return id > 0;
            }
        }*/
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public void delete(User user) {

    }
    @Override
    public void update(User user) {

    }
    public boolean query(User user) {
        String sql = "SELECT COUNT(*) FROM users WHERE name = ? AND password = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,user.getName() );
            pstmt.setString(2, user.getPassword());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users"; // 查询所有游戏数据

        try (Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


}
