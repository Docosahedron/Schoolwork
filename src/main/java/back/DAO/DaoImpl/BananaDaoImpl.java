package back.DAO.DaoImpl;

import back.DAO.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class BananaDaoImpl {
    public boolean init(String name){
        String sql = "INSERT INTO bananas (username,N,R,SR,SSR,UR)  values (?,0,0,0,0,0)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //某种类型的香蕉数量增加
    public boolean addNum(String username, List<String> list) {
        //哈希统计,提高性能
        HashMap<String,Integer> type = new HashMap<>();
        for (String s : list) type.merge(s, 1, Integer::sum);
        //加入的元素太多了,就不用通配符的方法,牺牲了安全性,容易被sql注入
        String pre = " where username = "+"\""+username+"\"";
        try (Connection conn = DBUtils.getConnection();) {
            conn.setAutoCommit(false);//禁止自动提交,防止多次添加中一次失败导致错误
            for (String s : type.keySet()) {
                int num = type.get(s);
                String sql = "update ignore bananas set "+s+" = "+s+" + "+num+pre;
                try(PreparedStatement ps = conn.prepareStatement(sql);){
                    if (!(ps.executeUpdate() > 0)){
                        conn.rollback();
                        return false;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    conn.rollback();
                    return false;
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //减少香蕉数量
    public boolean removeNum(String name, String type, int num) {
        String sql = "update ignore bananas set "+ type+" = "+type+" - "+num+
                " where username = ? and "+type+" - "+num+" >= 0";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name);
            return ps.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
