package back.SERVICE;

import back.ENTITY.Game;
import back.ENTITY.User;

public interface GameSer {
    void addWishlist(User user, Game game);
    boolean removeWishlist();

}
