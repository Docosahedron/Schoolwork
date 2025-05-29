package back.DAO;

import back.ENTITY.Review;

import java.util.List;

public interface ReviewDao {
    boolean addReview(Review review);
    List<Review> getByName(String name);
}
