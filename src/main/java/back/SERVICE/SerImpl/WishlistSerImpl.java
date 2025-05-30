package back.SERVICE.SerImpl;
import back.DAO.DaoImpl.*;
import back.ENTITY.*;
import front.GUI.wishlistFrame;

import javax.swing.*;
import java.util.List;

public class WishlistSerImpl {
    WishlistDaoImpl wd = new WishlistDaoImpl();
    //查询是否添加过
    public boolean queryAdded(User user,Game game) {
        return wd.queryGameByUser(user.getName(), game.getName());
    }

    //游戏加入心愿单
    public boolean addWishlist(User u, Game g) {
        boolean flag= wd.add(u.getName(), g.getName());
        if(flag) JOptionPane.showMessageDialog(null,"添加成功!");
        else JOptionPane.showMessageDialog(null,"添加失败!");
        return flag;
    }
    public List<Game> getAllWishlistGame(User u) {
        return wd.getAllGames(u.getName());
    }

    //删除心愿单选中游戏
    public boolean removeWishlist(User u, Game g) {
        boolean flag = wd.removeOne(u.getName(), g.getName());
        if(flag) JOptionPane.showMessageDialog(null,"移除成功!");
        else JOptionPane.showMessageDialog(null,"移除失败!");
        return flag;
    }

    //删除心愿单所有游戏
    public void removeWishlistAll(User u) {
        boolean flag = wd.removeAll(u.getName());
        if (flag) {
            JOptionPane.showMessageDialog(null, "已成功清空心愿单!！");
        } else {
            JOptionPane.showMessageDialog(null, "移除失败！");
        }
    }
}
