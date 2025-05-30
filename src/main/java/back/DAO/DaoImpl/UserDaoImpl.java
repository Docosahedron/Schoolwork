package back.DAO.DaoImpl;
import back.DAO.*;
import back.ENTITY.*;
import java.sql.*;
import java.util.*;

public class UserDaoImpl implements UserDao {
    //添加用户
    @Override
    public boolean add(User user) {
        String sql = "INSERT INTO users (id,name, password,balance) VALUES (?,?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setDouble(4, 0);
            // 执行插入，返回受影响的行数,如果只需要知道是否成功
            return ps.executeUpdate() > 0;
        /* 如果需要获取生成的ID
        try (ResultSet rs = ps.getGeneratedKeys()) {
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
    //删除用户
    @Override
    public void delete(User user) {

    }
    //更新用户信息
    @Override
    public void update(User user) {

    }
    //查询是否有此用户
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
    //获取某个用户的所有信息
    public User getOne(User u) {
        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,u.getName() );
            pstmt.setString(2, u.getPassword());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getDouble("balance"),
                            rs.getInt("package"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取所有用户的所有信息
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
    //更新余额
    public boolean updateBalance(User user, double price) {
        String sql = "update ignore users set balance = balance + ? where name = ? and balance + ? >= 0";
        try {Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, price);
            ps.setString(2, user.getName());
            ps.setDouble(3, price);
            return ps.executeUpdate()> 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //更新香蕉袋数量
    public boolean updatePackage(String name, int num) {
        String sql = "update ignore users set package = package + ? where name = ? and package + ? >= 0";
        try {Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, num);
            ps.setString(2, name);
            ps.setDouble(3, num);
            return ps.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
