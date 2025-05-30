package back.ENTITY;

public class Banana {
    private String username;
    private int N;
    private int R;
    private int SR;
    private int SSR;
    private int UR;
    public Banana(String username, int N, int R, int SR, int SSR, int UR) {
        this.username = username;
        this.N = N;
        this.R = R;
        this.SR = SR;
        this.SSR = SSR;
        this.UR = UR;
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
}
