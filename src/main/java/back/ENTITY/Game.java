package back.ENTITY;

public class Game {
    private String name;
    private String type;
    private int score;
    private double price;
    private int num;
    private String overview;
    public Game(String name, String type, double price,int num) {
        this.name = name;
        this.type = type;
        this.score = -1;
        this.price = price;
        this.num = num;
        this.overview = "null";
    }
    public Game(String name, String type, int score,double price,int num, String overview) {
        this.name = name;
        this.type = type;
        this.score = score;
        this.price = price;
        this.num = num;
        this.overview = overview;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType(){return type;}
    public void setType(String type){this.type = type;}
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getScore(){return score;}
    public void setScore(int score){this.score = score;}
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getOverview(){return overview;}
    public void setOverview(String overview){this.overview = overview;}
    @Override
    public String toString(){
        return "Game [name=" + name + ", type=" + type + ", price=" + price + ", num=" + num+"]";
    }
}
