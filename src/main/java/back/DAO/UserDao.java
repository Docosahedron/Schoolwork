package back.DAO;
import back.ENTITY.User;
import java.util.*;

public interface UserDao {
    boolean add(User user);
    void delete(User user);
    void update(User user);
    boolean  query(User user);
    List<User> getAll();
}
