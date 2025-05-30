package back.ENTITY;

public class BananaTemp {
    private String username;
    private String bananaType;
    private double price;
    private int num;
    public BananaTemp(String username, String type, double price, int num) {
        this.username = username;
        this.bananaType = type;
        this.price = price;
        this.num = num;
    }

    public String getUsername() {
        return username;
    }

    public String getBananaType() {
        return bananaType;
    }

    public double getPrice() {
        return price;
    }

    public int getNum() {
        return num;
    }
}
