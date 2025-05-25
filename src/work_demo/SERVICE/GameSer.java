package work_demo.SERVICE;
import work_demo.DAO.*;
import work_demo.ENTITY.Game;
import work_demo.ENTITY.User;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class GameSer implements GameDao {
    public boolean add(Game game){
        String sql = "INSERT INTO games (name,type,sorce,price,num) VALUES (?,?, ?, ?,?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, game.getName());
            pstmt.setString(2, game.getType());
            pstmt.setDouble(3, game.getPrice());
            pstmt.setDouble(4,game.getScore());
            pstmt.setInt(5, game.getNum());
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
    public boolean addWishlist(Game game, User user) {
        String sql = "INSERT INTO wishlist (username, gamename, num, time) VALUES (?, ?, 1, ?) " +
                "ON DUPLICATE KEY UPDATE num = num + 1, time = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Date currentDate = Date.valueOf(LocalDate.now());
            pstmt.setString(1, user.getName());
            pstmt.setString(2, game.getName());
            pstmt.setDate(3, currentDate);
            pstmt.setDate(4, currentDate);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(int id) {
        return true;
    }
    public boolean update(int id) {
        return true;
    }
    public List<Game> query(int id) {
        return List.of();
    }
    //检索游戏
    public List<Game> getByType(String type) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE type LIKE ?"; // 使用LIKE进行模糊查询

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置参数，使用%实现模糊匹配
            pstmt.setString(1, "%" + type + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Game game = new Game(
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getDouble("price"),
                            rs.getInt("num")
                    );
                    games.add(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }
    //检索愿望单
    public List<Game> getByUser(String name) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT name,price,type,score,wishlist.num FROM wishlist " +
                "join games on wishlist.gameName=games.name " +
                "WHERE username = ? order by wishlist.time desc" ;
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Game game = new Game(
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getDouble("price"),
                            rs.getInt("num")
                    );
                    games.add(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }
    //获得所有游戏
    public List<Game> getAll() {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games"; // 查询所有游戏数据

        try (Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Game game = new Game(rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getInt("num"));
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

}
