package back.DAO;

import back.ENTITY.Game;

import java.util.List;

public interface WDao {
    boolean add(String username, String gameName);
    boolean queryGameByUser(String username, String gameName);
    List<Game> getAllGames(String username);
}
