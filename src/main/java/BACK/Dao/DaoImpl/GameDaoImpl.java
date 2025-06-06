package BACK.Dao.DaoImpl;
import BACK.Dao.*;
import BACK.Entity.Game;

import java.sql.*;
import java.util.*;

public class GameDaoImpl implements GameDao {
    //添加游戏
    public boolean add(Game game){
        String sql = "INSERT INTO games (name,type,score,price) VALUES (?,?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, game.getName());
            ps.setString(2, game.getType());
            ps.setInt(3,game.getScore());
            ps.setDouble(4, game.getPrice());
            // 执行插入，返回受影响的行数
            int affectedRows = ps.executeUpdate();
            // 如果只需要知道是否成功
            return affectedRows > 0;
        /* 如果需要获取生成的ID
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                int id = rs.getInt(1); // 获取自增ID
                return id > 0;
            }
        }*/
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库异常,添加游戏失败");
            return false;
        }
    }
    //删除游戏
    public boolean delete(int id) {
        return true;
    }
    //更新游戏信息
    public boolean update(int id) {
        return true;
    }

    public boolean query(int id) {
        return false;
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
                            rs.getString("overview")
                    );
                    games.add(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库异常,检索游戏失败");
        }
        return games;
    }
    //从名字检索游戏
    public Game getInfoByName(String name) {
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
                            rs.getString("overview")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库异常,检索游戏失败");
        }
        return game;
    }
    //精选游戏
    public List<Game> getByScore(int score) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE score >= ? order by score desc";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, score);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Game game = new Game(
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getInt("score"),
                            rs.getDouble("price"),
                            rs.getString("overview")
                    );
                    games.add(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库异常,检索游戏失败");
        }
        return games;
    }
    //获取未购买过的游戏
    public List<Game> getUnbought(String name) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE name NOT IN (SELECT gameName FROM warehouse WHERE username = ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Game game = new Game(
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getInt("score"),
                            rs.getDouble("price"),
                            rs.getString("overview")
                    );
                    games.add(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库异常,检索游戏失败");
        }
        return games;
    }
    //获取所有游戏的信息
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
                        rs.getString("overview")
                );
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库异常,检索游戏失败");
        }
        return games;
    }

}