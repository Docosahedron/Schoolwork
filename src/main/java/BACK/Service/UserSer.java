package BACK.Service;

import BACK.Entity.Game;
import BACK.Entity.User;

public interface UserSer {
    boolean login(User user);
    boolean register(User user);
    boolean buy(User user, Game game);

}
