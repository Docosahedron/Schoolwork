package work_demo.DAO;
import work_demo.ENTITY.User;
import java.util.*;

public interface UserDao {
    boolean add(User user);
    void delete(User user);
    void update(User user);
    boolean  query(User user);
    List<User> getAll();
}
