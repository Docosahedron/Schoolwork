package back.SERVICE;

import back.ENTITY.Game;
import back.ENTITY.User;

public interface UserSer {
    boolean login(User user);
    boolean register(User user);
    void buy(User user, Game game);

}
