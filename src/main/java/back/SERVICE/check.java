package back.SERVICE;

class check {
    public boolean checkNum(String num) {
        return num.matches("[0-9]*");
    }
    public boolean checkPrice(String price){
        return price.matches("[0-9]*");
    }
}
