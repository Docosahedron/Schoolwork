package back.SERVICE.SerImpl;

import java.util.List;

import back.DAO.DaoImpl.WarehouseDaoImpl;
import back.ENTITY.Game;
import back.ENTITY.User;

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
