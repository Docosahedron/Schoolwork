package BACK.Service.SerImpl;

import BACK.Dao.DaoImpl.*;
import BACK.Entity.*;
import java.util.*;

public class ReviewSerImpl {
    ReviewDaoImpl rd = new ReviewDaoImpl();
    //通过游戏名字获取评论
    public List<Review> getReviews(Game game) {
        return rd.getByGameName(game.getName());
    }
}
