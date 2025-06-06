package BACK.Service.SerImpl;

import BACK.Dao.DaoImpl.*;
import BACK.Entity.*;
import BACK.Service.*;
import javax.swing.*;
import java.util.*;

public class GameSerImpl implements GameSer {
    GameDaoImpl gd = new GameDaoImpl();
    //添加游戏到系统
    public boolean addGame(Game g) {
        if (gd.add(g)) {
            JOptionPane.showMessageDialog(null, "添加成功!");
            return true;
        }else {JOptionPane.showMessageDialog(null, "添加失败!");
            return false;
        }
    }

    //通过游戏名查询游戏所有信息
    public Game getWholeInfo(String name) {
        return gd.getByName(name);
    }
    //获取检索后所有游戏
    public List<Game> getGameBySearch(String type, double min, double max) {
        return gd.getBySearch(type, min, max);
    }

    //获取精选游戏
    public List<Game> getGameByScore(int score){
        return gd.getByScore(score);
    }

}