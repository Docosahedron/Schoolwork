package back.DAO.DaoImpl;

import back.DAO.DBUtils;
import back.DAO.WDao;
import back.ENTITY.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishlistDaoImpl implements WDao {
    //添加心愿单
    @Override
    public boolean add(String username, String gameName) {
        // 使用ON DUPLICATE KEY UPDATE实现原子操作
        String sql = "INSERT ignore INTO wishlist (username, gameName, time) " +
                "VALUES (?, ?, NOW()) ";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, gameName);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean queryGameByUser(String username, String gameName) {
        String sql = "select count(*)  from wishlist  where username = ? and gameName = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, gameName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1); // ✅ 现在可以取数据了
                return count >0;
            }else return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //移除心愿单一个游戏
    public boolean removeOne(String username, String gameName) {
            String sql = "delete from wishlist  where username = ? and gameName = ?";
            try (Connection conn = DBUtils.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, gameName);
                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        // 使用ON DUPLICATE KEY UPDATE实现原子操作
    }
    //删除心愿单所有游戏
    public boolean removeAll(String username) {
        // 使用ON DUPLICATE KEY UPDATE实现原子操作
        String sql = "delete from wishlist where username = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //获取心愿单所有游戏
    public List<Game> getAllGames(String name) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT name,price,type,score,overview FROM wishlist " +
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
}
