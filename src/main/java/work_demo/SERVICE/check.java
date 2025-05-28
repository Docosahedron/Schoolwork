package work_demo.SERVICE;
import work_demo.DAO.*;
import work_demo.ENTITY.*;

class check {
    public boolean checkNum(String num) {
        return num.matches("[0-9]*");
    }
    public boolean checkPrice(String price){
        return price.matches("[0-9]*");
    }
}
