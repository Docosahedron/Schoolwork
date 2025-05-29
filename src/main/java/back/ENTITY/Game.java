package back.ENTITY;

public class Game {
    private String name;
    private String type;
    private int score;
    private double price;
    private String overview;
    public Game(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.score = -1;
        this.price = price;
        this.overview = "null";
    }
    public Game(String name, String type, int score,double price, String overview) {
        this.name = name;
        this.type = type;
        this.score = score;
        this.price = price;
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
    public String getOverview(){return overview;}
    public void setOverview(String overview){this.overview = overview;}
    @Override
    public String toString(){
        return "Game [name=" + name + ", type=" + type + ", price=" + price + ", score=]";
    }
}
