package back.SERVICE.SerImpl;

import back.DAO.DaoImpl.ReviewDaoImpl;
import back.DAO.ReviewDao;
import back.ENTITY.Game;
import back.ENTITY.Review;

import java.util.List;

public class ReviewSerImpl {
    ReviewDao rd = new ReviewDaoImpl();
    //通过游戏名字获取评论
    public List<Review> getReviews(Game game) {
        return rd.getByName(game.getName());
    }
}
