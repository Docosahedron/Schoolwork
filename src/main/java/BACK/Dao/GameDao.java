package BACK.Dao;

import BACK.Entity.Game;
import java.util.*;

public interface GameDao {
    boolean add(Game game);
    boolean delete(int id);
    boolean update(int id);
    boolean query(int id);
    List<Game> getAll();
}
