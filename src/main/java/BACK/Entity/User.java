package BACK.Entity;

public class User {
    private int id;
    private String name;
    private String password;
    private double balance=0.0;
    private int packages=0;
    //全参构造方法,一般只用来获取全部信息
    public User(int id, String name, String password, double balance, int packages) {
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
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public int getPackages(){
        return packages;
    }
    public void setPackages(int packages){
        this.packages=packages;
    }
}
