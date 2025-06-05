package BACK.Service.SerImpl;

import java.util.List;

import BACK.Dao.DaoImpl.WarehouseDaoImpl;
import BACK.Entity.Game;
import BACK.Entity.User;

public class WarehouseSerImpl{
    WarehouseDaoImpl whd =  new WarehouseDaoImpl();
    public boolean queryBought(User user, Game game) {
        return whd.queryGameByUser(user.getName(), game.getName());
    }
    //获取用户所有游戏
    public List<Game> getGames(String name){
        return whd.getAllGames(name);
    }
}
