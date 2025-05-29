package back.SERVICE.SerImpl;

import back.DAO.DaoImpl.GameDaoImpl;
import back.ENTITY.Game;
import back.ENTITY.User;
import back.SERVICE.GameSer;

import javax.swing.*;
import java.util.List;

public class GameSerImpl implements GameSer {
    GameDaoImpl gdi = new GameDaoImpl();
    @Override
    public void addWishlist(User u, Game g) {
        boolean flag= gdi.addWishlist(u, g);
        if(flag) JOptionPane.showMessageDialog(null,"添加成功!");
        else JOptionPane.showMessageDialog(null,"添加失败!");
    }

    @Override
    public boolean removeWishlist() {

        return false;
    }
    public boolean addGame(Game g) {
        if (gdi.add(g)) {
            JOptionPane.showMessageDialog(null, "添加成功!");
            return true;
        }else {JOptionPane.showMessageDialog(null, "添加失败!");
            return false;
        }
    }
//    public List<Game> getGameBySearch(String type,double min,double max) {
//        return gdi.getBySearch(type, min, max);
//    }

}
