package BACK.Service;

public class Check {
    public boolean checkUserName(String userName) {
        return userName.matches("^[a-zA-Z0-9_\\u4e00-\\u9fa5]{4,10}$");
    }
    public boolean checkPassword(String password){
        return password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]{8,16}$");
    }
    public boolean checkNum(String num) {
        return num.matches("[0-9]*");
    }
    public boolean checkPrice(String price){
        return price.matches("[0-9]*");
    }
}
