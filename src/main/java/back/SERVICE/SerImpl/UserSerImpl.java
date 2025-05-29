package back.SERVICE.SerImpl;
import back.DAO.DaoImpl.*;
import back.ENTITY.*;
import front.GUI.*;
import back.SERVICE.*;
import javax.swing.*;

public class UserSerImpl implements UserSer {
    UserDaoImpl ud = new UserDaoImpl();
    WarehouseDaoImpl wd = new WarehouseDaoImpl();

    //用户登录
    @Override
    public boolean login(User tempUser) {
        if (tempUser.getName().equals( "ad") && tempUser.getPassword().equals( "ad" )) {
            new AdminFrame();
            return true;
        }
        else if(ud.query(tempUser)) {
            new UserFrame(ud.getOne(tempUser));
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "用户名或密码错误!");
            return false;
        }

    }

    //后期注册要验证id是否重复
    @Override
    public boolean register(User u){
        if (ud.add(u)){
            JOptionPane.showMessageDialog(null, "注册成功!");
            new loginFrame();
            return true;
        }else {
            JOptionPane.showMessageDialog(null, "注册失败!");
            return false;
        }

    }

    //用户购买
    @Override
    public boolean buy(User curUser, Game curGame){
        int flag= JOptionPane.showConfirmDialog(null,"是否确认购买?");
        if(flag==JOptionPane.YES_OPTION){
            if(ud.updateBalance(curUser,-curGame.getPrice())){
                curUser.setBalance(curUser.getBalance()-curGame.getPrice());
                wd.add(curUser.getName(), curGame.getName());
                JOptionPane.showMessageDialog(null,"购买成功!");
                return true;
            }else {
                JOptionPane.showMessageDialog(null,"余额不足,购买失败!");
                return false;
            }
        }
        return false;
    }

    //用户充值
    public void recharge(User curUser,double price){
        if(ud.updateBalance(curUser,price)){
            curUser.setBalance(curUser.getBalance()+price);
            JOptionPane.showMessageDialog(null,"充值成功!");
        }else {
            JOptionPane.showMessageDialog(null,"充值失败!");
        }
    }
}
