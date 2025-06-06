package BACK.Dao.DaoImpl;

import BACK.Dao.DBUtils;
import BACK.Dao.ReviewDao;
import BACK.Entity.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDaoImpl implements ReviewDao {
    //添加评论
    @Override
    public boolean add(Review review) {
        return false;
    }
    //获取某个游戏的评论

    public List<Review> getByGame(String gameName) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE gameName = ? order by time desc"; // 精确查询
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // 设置参数，
            ps.setString(1, gameName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review(rs.getString("gameName"),
                            rs.getString("content"),
                            rs.getString("author"),
                            rs.getDate("time")
                    );
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库异常,获取游戏评论失败");
        }
        return reviews;
    }
    //删除评论
    @Override
    public boolean remove(Review review) {
        return false;
    }
}