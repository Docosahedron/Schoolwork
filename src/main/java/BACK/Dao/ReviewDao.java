package BACK.Dao;

import BACK.Entity.Review;

import java.util.List;

public interface ReviewDao {
    boolean add(Review review);
    boolean remove(Review review);
    List<Review> getByName(String name);
}
