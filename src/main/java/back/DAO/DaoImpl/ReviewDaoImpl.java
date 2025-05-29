package back.DAO.DaoImpl;

import back.DAO.DBUtils;
import back.DAO.ReviewDao;
import back.ENTITY.Review;

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
    @Override
    public List<Review> getByName(String name) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE gameName = ? order by time desc"; // 精确查询
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数，
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
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
        }
        return reviews;
    }
    //删除评论
    @Override
    public boolean remove(Review review) {
        return false;
    }
}
