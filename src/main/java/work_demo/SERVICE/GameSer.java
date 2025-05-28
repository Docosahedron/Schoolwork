package work_demo.SERVICE;
import work_demo.DAO.*;
import work_demo.ENTITY.Game;
import work_demo.ENTITY.User;

import java.sql.*;
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
        // 使用ON DUPLICATE KEY UPDATE实现原子操作
        String sql = "INSERT INTO wishlist (username, gameName, num, time) " +
                "VALUES (?, ?, 1, NOW()) " +
                "ON DUPLICATE KEY UPDATE num = num + 1, time = NOW()";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, game.getName());

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
    public List<Game> getBySearch(String type,double min,double max) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE type LIKE ? and price >= ? and price <= ?"; // 使用LIKE进行模糊查询

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置参数，使用%实现模糊匹配
            //后期改为多选按钮筛选,可以更精确类型
            pstmt.setString(1, "%" + type + "%");
            pstmt.setDouble(2, min);
            pstmt.setDouble(3, max);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Game game = new Game(
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getInt("score"),
                            rs.getDouble("price"),
                            rs.getInt("num"),
                            rs.getString("overview")
                    );
                    games.add(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }
    public Game getByName(String name) {
        Game game = null;
        String sql = "SELECT * FROM games WHERE name = ? ";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    game = new Game(
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getInt("score"),
                            rs.getDouble("price"),
                            rs.getInt("num"),
                            rs.getString("overview")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }
    //检索愿望单
    public List<Game> getByUser(String name) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT name,price,type,score,wishlist.num,games.overview FROM wishlist " +
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
                            rs.getInt("score"),
                            rs.getDouble("price"),
                            rs.getInt("num"),
                            rs.getString("overview")
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
                Game game = new Game(
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("score"),
                        rs.getDouble("price"),
                        rs.getInt("num"),
                        rs.getString("overview")
                );
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

}
