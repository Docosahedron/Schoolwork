package BACK.Dao.DaoImpl;

import BACK.Dao.*;
import BACK.Entity.*;

import java.sql.*;
import java.util.*;

public class BananaDaoImpl {
    //注册时初始化表
    public boolean init(String name){
        String sql = "INSERT INTO bananas (username,N,R,SR,SSR,UR)  values (?,0,0,0,0,0) ";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库异常,初始化香蕉失败");
            return false;
        }
    }
    //增加某种类型的香蕉数量
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
            System.err.println("数据库异常,添加香蕉失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //减少香蕉数量
    public boolean removeNum(String name, String type, int num) {
        String sql = " update ignore bananas set "+ type+" = "+type+" - "+num+
                " where username = ? and "+type+" - "+num+" >= 0 ";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name);
            return ps.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库异常,减少香蕉失败");
            return false;
        }
    }
    
    // 获取用户的香蕉数量
    public Banana getNum(String username) {
        Banana b = null;
        String sql = " SELECT * FROM bananas WHERE username = ? ";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    b = new Banana(
                            rs.getString("username"),
                            rs.getInt("N"),
                            rs.getInt("R"),
                            rs.getInt("SR"),
                            rs.getInt("SSR"),
                            rs.getInt("UR")
                    );
                }
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库异常,获取用户香蕉失败");
        }
        return b;
    }
}
