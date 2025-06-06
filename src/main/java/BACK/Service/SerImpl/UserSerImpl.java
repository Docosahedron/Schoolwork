package BACK.Service.SerImpl;
import javax.swing.*;
import java.util.*;

import BACK.Dao.DaoImpl.*;
import BACK.Entity.*;
import BACK.Service.*;
import FRONT.GUI.*;

public class UserSerImpl implements UserSer {
    UserDaoImpl ud = new UserDaoImpl();
    BananaDaoImpl bd = new BananaDaoImpl();
    WarehouseDaoImpl wd = new WarehouseDaoImpl();
    //用户登录
    @Override
    public boolean login(User enrollee) {
        if (enrollee.getName().equals( "ad") && enrollee.getPassword().equals( "ad" )) {
            new AdminFrame();
            return true;
        }
        else if(ud.query(enrollee.getName(),enrollee.getPassword())) {
            new UserFrame(ud.getInfoByName(enrollee.getName()));
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
            if(ud.updateBalance(curUser.getName(),-curGame.getPrice())){
                curUser.setBalance(curUser.getBalance()-curGame.getPrice());
                wd.add(curUser.getName(), curGame.getName());

                // 购买奖励机制：每消费1元有10%获得一个香蕉包
                int reward = 0;
                for(int i=0;i<curGame.getPrice();i++) if (Math.random()<0.1) reward++;

                if (reward > 0) {
                    ud.updatePackage(curUser.getName(), reward);
                    curUser.setPackages(curUser.getPackages() + reward);
                    JOptionPane.showMessageDialog(null,
                            "购买成功!\n恭喜您获得 " + reward + " 个香蕉包!");
                } else JOptionPane.showMessageDialog(null, "购买成功!\n很遗憾本次没有获得香蕉包");
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
        if(ud.updateBalance(curUser.getName(),price)){
            curUser.setBalance(curUser.getBalance()+price);
            JOptionPane.showMessageDialog(null,"充值成功!");
            new WalletFrame(curUser);
            return true;
        }else {
            JOptionPane.showMessageDialog(null,"充值失败!");
            return false;
        }
    }
    //获取用户信息
    public User getUserInfo(String name){
        return ud.getInfoByName(name);
    }

    //获取用户的香蕉信息
    public Banana getBanana(User curUser){
        return bd.getNum(curUser.getName());
    }

    //开包
    public boolean openPackage(User curUser,int times) {
        List<String> list = new ArrayList<>();
        if (!ud.updatePackage(curUser.getName(), -times)) {
            JOptionPane.showMessageDialog(null,"香蕉包数量不足!");
            return false;
        }
        curUser.setPackages(curUser.getPackages() - times);

        StringBuilder result = new StringBuilder("开包结果:\n");
        int countN = 0, countR = 0, countSR = 0, countSSR = 0, countUR = 0;
        Random r= new Random();
        while (times-- > 0) {
            int n = r.nextInt(1000)+1;
            if (n <= 600) {list.add("N");countN++;}
            else if (n <= 850) {list.add("R");countR++;}
            else if (n <= 950) {list.add("SR");countSR++;}
            else if (n <= 995) {list.add("SSR");countSSR++;}
            else {list.add("UR");countUR++;}
        }
        if (countN > 0) result.append("N: ").append(countN).append("个\n");
        if (countR > 0) result.append("R: ").append(countR).append("个\n");
        if (countSR > 0) result.append("SR: ").append(countSR).append("个\n");
        if (countSSR > 0) result.append("SSR: ").append(countSSR).append("个\n");
        if (countUR > 0) result.append("UR: ").append(countUR).append("个\n");
        if (bd.addNum(curUser.getName(), list)) {
            JOptionPane.showMessageDialog(null, result.toString());
            return true;
        }
        return false;
    }

    // 购买香蕉包
    public boolean buyPackage(User curUser, int amount) {
        double totalPrice = amount * 2.0; // 2元一个包
        if (curUser.getBalance() < totalPrice) {
            JOptionPane.showMessageDialog(null, "余额不足，无法购买!");
            return false;
        }
        if (ud.updateBalance(curUser.getName(), -totalPrice)) {
            curUser.setBalance(curUser.getBalance() - totalPrice);
            // 更新香蕉包数量
            if (ud.updatePackage(curUser.getName(), amount)) {
                curUser.setPackages(curUser.getPackages() + amount);
                JOptionPane.showMessageDialog(null, "成功购买 " + amount + " 个香蕉包!");
                return true;
            } else {
                ud.updateBalance(curUser.getName(), totalPrice);
                curUser.setBalance(curUser.getBalance() + totalPrice);
                JOptionPane.showMessageDialog(null, "购买失败: 系统无法更新香蕉包数量!");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "购买失败: 系统无法更新余额!");
            return false;
        }
    }

    // 出售香蕉
    public boolean sellBanana(User curUser,Banana banana) {
        if(!bd.removeNum(banana.getUsername(), banana.getType(), banana.getNum())){
            JOptionPane.showMessageDialog(null,"出售失败,数量不足!");
            return false;
        }
        double earned = banana.getNum() * getBananaPrice(banana.getType());
        if (ud.updateBalance(banana.getUsername(), earned)) {
            curUser.setBalance(curUser.getBalance() + earned);
            JOptionPane.showMessageDialog(null,"出售成功! 获得 " + earned + " 元");
            return true;
        }
        JOptionPane.showMessageDialog(null,"出售失败!");
        return false;
    }

    // 获取香蕉价格
    public double getBananaPrice(String type) {
        return switch (type) {
            case "N" -> 0.1;
            case "R" -> 1.0;
            case "SR" -> 5.0;
            case "SSR" -> 20.0;
            case "UR" -> 100.0;
            default -> 0;
        };
    }
}