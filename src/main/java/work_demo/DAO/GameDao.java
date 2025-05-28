package work_demo.DAO;

import work_demo.ENTITY.Game;
import java.util.*;

public interface GameDao {
    boolean add(Game good);
    boolean delete(int id);
    boolean update(int id);
    List<Game> query(int id);
    List<Game> getAll();
}
