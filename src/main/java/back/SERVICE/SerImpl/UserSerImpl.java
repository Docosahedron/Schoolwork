package back.SERVICE.SerImpl;
import back.DAO.DaoImpl.*;
import back.ENTITY.*;
import front.GUI.*;
import back.SERVICE.*;
import javax.swing.*;

public class UserSerImpl implements UserSer {
    UserDaoImpl udi = new UserDaoImpl();
    @Override
    public boolean login(User tempUser) {
        if (tempUser.getName().equals( "ad") && tempUser.getPassword().equals( "ad" )) {
            new AdminFrame();
            return true;
        }
        else if(udi.query(tempUser)) {
            new UserFrame(udi.getOne(tempUser));
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
        if (udi.add(u)){
            JOptionPane.showMessageDialog(null, "注册成功!");
            new loginFrame();
            return true;
        }else {
            JOptionPane.showMessageDialog(null, "注册失败!");
            return false;
        }

    }
    public void buy(User curUser, Game curGame){
        int flag= JOptionPane.showConfirmDialog(null,"是否确认购买?");
        if(flag==JOptionPane.YES_OPTION){
            if(udi.updateBalance(curUser,curGame.getPrice())){
                JOptionPane.showMessageDialog(null,"购买成功!");
                curUser.setBalance(curUser.getBalance()-curGame.getPrice());
            }else {
                JOptionPane.showMessageDialog(null,"余额不足,购买失败!");
            }
        }
    }
    public void recharge(User curUser,double price){
        if(udi.updateBalance(curUser,price)){
            curUser.setBalance(curUser.getBalance()+price);
            JOptionPane.showMessageDialog(null,"充值成功!");
        }else {
            JOptionPane.showMessageDialog(null,"充值失败!");
        }
    }
}
