package BACK.Dao;
import BACK.Entity.User;
import java.util.*;

public interface UserDao {
    boolean add(User user);
    void delete(User user);
    void update(User user);
    boolean  query(String username,String password);
    User getInfo(String username);
    List<User> getAllInfo();
}
