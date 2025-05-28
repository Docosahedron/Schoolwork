package work_demo.SERVICE;
import work_demo.DAO.*;
import work_demo.ENTITY.Admin;
import java.util.*;

public class AdminSer implements AdminDao {

    @Override
    public List<Admin> getAdmin(Admin admin){
        String sql = "select * from admins where name=? and password=?";
        List<Admin> adminList = new ArrayList<Admin>();

        return adminList;
    }
}
