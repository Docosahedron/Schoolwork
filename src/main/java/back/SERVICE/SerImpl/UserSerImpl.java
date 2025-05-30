package back.SERVICE.SerImpl;
import back.DAO.DaoImpl.*;
import back.ENTITY.*;
import front.GUI.*;
import back.SERVICE.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UserSerImpl implements UserSer {
    UserDaoImpl ud = new UserDaoImpl();
    BananaDaoImpl bd = new BananaDaoImpl();
    WarehouseDaoImpl wd = new WarehouseDaoImpl();//用户登录
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
        if (ud.add(u)&&bd.init(u.getName())){
            JOptionPane.showMessageDialog(null, "注册成功!");
            new LoginFrame();
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
    public boolean recharge(User curUser,double price){
        if(ud.updateBalance(curUser,price)){
            curUser.setBalance(curUser.getBalance()+price);
            JOptionPane.showMessageDialog(null,"充值成功!");
            new WalletFrame(curUser);
            return true;
        }else {
            JOptionPane.showMessageDialog(null,"充值失败!");
            return false;
        }
    }
    //开包
    public boolean openPackage(User curUser,int times) {
        List<String> list = new ArrayList<>();
        if (!ud.updatePackage(curUser.getName(), -times)) {
            JOptionPane.showMessageDialog(null,"香蕉包数量不足!");
            return false;
        }
        while (times-- > 0) {
            int r = (int) (Math.random() * 100) + 1;
            if (r <= 50) list.add("N");
            else if (r <= 80) list.add("R");
            else if (r <= 95) list.add("SR");
            else if (r <= 99) list.add("SSR");
            else if (r == 100) list.add("UR");
        }
        return bd.addNum(curUser.getName(), list);
    }
    public boolean sellBanana(User curUser, BananaTemp bt) {
        if(!bd.removeNum(curUser.getName(), bt.getBananaType(),-bt.getNum())){
            JOptionPane.showMessageDialog(null,"出售失败,数量不足!");
            return false;
        }
        curUser.setBalance(curUser.getBalance()+bt.getNum()*bt.getPrice());
        JOptionPane.showMessageDialog(null,"出售成功!");
        return true;
    }
}
