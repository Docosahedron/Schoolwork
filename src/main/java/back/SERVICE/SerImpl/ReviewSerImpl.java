package back.SERVICE.SerImpl;

import back.DAO.DaoImpl.*;
import back.ENTITY.*;
import java.util.*;

public class ReviewSerImpl {
    ReviewDaoImpl rd = new ReviewDaoImpl();
    //通过游戏名字获取评论
    public List<Review> getReviews(Game game) {
        return rd.getByName(game.getName());
    }
}
