package work_demo.DAO;

import work_demo.ENTITY.Review;

import java.util.List;

public interface ReviewDao {
    boolean addReview(Review review);
    List<Review> getReviews(String name);
}
