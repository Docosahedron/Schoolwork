package BACK.Service.SerImpl;
import BACK.Dao.DaoImpl.*;
import BACK.Entity.*;

import javax.swing.*;
import java.util.List;

public class WishlistSerImpl {
    WishlistDaoImpl wd = new WishlistDaoImpl();
    //查询是否添加过
    public boolean queryAdded(User user,Game game) {
        return wd.queryGameByUser(user.getName(), game.getName());
    }

    //查询心愿单所有游戏
    public List<Game> getAllWishlistGame(User user) {
        return wd.getAllGames(user.getName());
    }

    //游戏加入心愿单
    public boolean addWishlist(User u, Game g) {
        boolean flag= wd.add(u.getName(), g.getName());
        if(flag) JOptionPane.showMessageDialog(null,"添加成功!");
        else JOptionPane.showMessageDialog(null,"添加失败!");
        return flag;
    }

    //删除心愿单选中游戏
    public boolean removeSelected(User u, Game g) {
        boolean flag = wd.removeOne(u.getName(), g.getName());
        if(flag) JOptionPane.showMessageDialog(null,"移除成功!");
        else JOptionPane.showMessageDialog(null,"移除失败!");
        return flag;
    }

    //删除心愿单所有游戏
    public void removeWishlist(User u) {
        boolean flag = wd.removeAll(u.getName());
        if (flag) JOptionPane.showMessageDialog(null, "已成功清空心愿单!！");
        else JOptionPane.showMessageDialog(null, "移除失败！");
    }
}