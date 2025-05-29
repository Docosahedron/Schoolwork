package back.DaoImpl;

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
    @Override
    public boolean addReview(Review review) {
        return false;
    }

    @Override
    public List<Review> getReviews(String name) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE gameName = ?"; // 精确查询
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
}
