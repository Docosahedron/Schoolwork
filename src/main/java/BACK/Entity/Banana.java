package BACK.Entity;

public class Banana {
    private String username;
    private int N;
    private int R=0;
    private int SR=0;
    private int SSR=0;
    private int UR=0;
    private String type;private int num;
    public Banana(String username, int N, int R, int SR, int SSR, int UR) {
        this.username = username;
        this.N = N;
        this.R = R;
        this.SR = SR;
        this.SSR = SSR;
        this.UR = UR;
    }
    //卖香蕉时临时构造类
    public Banana(String username,String type,int num){
        this.username = username;
        this.type = type;
        this.num = num;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public int getSR() {
        return SR;
    }

    public void setSR(int SR) {
        this.SR = SR;
    }

    public int getSSR() {
        return SSR;
    }

    public void setSSR(int SSR) {
        this.SSR = SSR;
    }

    public int getUR() {
        return UR;
    }

    public void setUR(int UR) {
        this.UR = UR;
    }

    public String getType(){
        return this.type;
    }

    public int getNum(){
        return num;
    }
}
