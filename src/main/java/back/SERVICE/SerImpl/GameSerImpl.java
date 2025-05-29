package back.SERVICE.SerImpl;

import back.DAO.DaoImpl.GameDaoImpl;
import back.ENTITY.Game;
import back.ENTITY.User;
import back.SERVICE.GameSer;
import javax.swing.*;
import java.util.List;

public class GameSerImpl implements GameSer {
    GameDaoImpl gdi = new GameDaoImpl();
    //添加进心愿单
    @Override
    public void addWarehouse(User u, Game g) {

    }

    //添加游戏到系统
    public boolean addGame(Game g) {
        if (gdi.add(g)) {
            JOptionPane.showMessageDialog(null, "添加成功!");
            return true;
        }else {JOptionPane.showMessageDialog(null, "添加失败!");
            return false;
        }
    }

    //通过游戏名查询游戏所有信息
    public Game getWholeInfo(String name) {
        return gdi.getByName(name);
    }
    //获取检索后所有游戏
    public List<Game> getGameBySearch(String type, double min, double max) {
        return gdi.getBySearch(type, min, max);
    }

    //获取精选游戏
    public List<Game> getGameByScore(int score){
        return gdi.getByScore(score);
    }

}
