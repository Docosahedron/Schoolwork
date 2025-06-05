package BACK.Entity;

import java.math.BigDecimal;

public class User {
    private int id;
    private String name;
    private String password;
    private BigDecimal balance = BigDecimal.ZERO;
    private int packages=0;
    //全参构造方法,一般只用来获取全部信息
    public User(int id, String name, String password, BigDecimal balance, int packages) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.balance=balance;
        this.packages=packages;
    }
    //简单构造方法
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public int getPackages(){
        return packages;
    }
    public void setPackages(int packages){
        this.packages=packages;
    }
}
