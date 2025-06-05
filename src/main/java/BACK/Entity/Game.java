package BACK.Entity;

import java.math.BigDecimal;

public class Game {
    private String name;
    private String type;
    private int score=0;
    private BigDecimal price;
    private String overview="null";
    public Game(String name, String type, BigDecimal price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }
    public Game(String name, String type, int score, BigDecimal price, String overview) {
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
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public int getScore(){return score;}
    public void setScore(int score){this.score = score;}
    public String getOverview(){return overview;}
    public void setOverview(String overview){this.overview = overview;}
    @Override
    public String toString(){
        return "Game [name=" + name + ", type=" + type +", score="+score+
                ", price=" + price+ ", overview=" + overview + "]";
    }
}
