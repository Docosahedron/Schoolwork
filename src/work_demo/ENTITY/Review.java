package work_demo.ENTITY;
import java.sql.Date;

public class Review {
    private String gameName;
    private String author;
    private String content;
    private Date time;
    public Review(String gameName, String content, String author, Date time) {
        this.gameName = gameName;
        this.content = content;
        this.author = author;
        this.time = time;
    }
    public Review() {
    }
    public String getGameName(){
        return gameName;
    }
    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Date getTime(){
        return time;
    }
    public void setTime(Date time){
        this.time = time;
    }
}
