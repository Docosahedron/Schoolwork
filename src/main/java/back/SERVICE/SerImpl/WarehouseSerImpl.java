package back.SERVICE.SerImpl;

import back.DAO.DaoImpl.WarehouseDaoImpl;
import back.ENTITY.Game;
import back.ENTITY.User;

public class WarehouseSerImpl{
    WarehouseDaoImpl whd =  new WarehouseDaoImpl();
    public boolean queryBought(User user, Game game) {
        return whd.queryGameByUser(user.getName(), game.getName());
    }
}
