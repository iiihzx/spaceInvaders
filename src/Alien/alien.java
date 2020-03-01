

public class alien{
    static double x;
    static double y;
    static int life=100;
    static  int type;
    static int number;
    public void setX(double x1) {
        x = x1;
    }
    public void setY(double y1){
        y=y1;
    }

    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public static void setNumber(int n) {
        alien.number = n;
    }

    public static int getnum() {
        return number;
    }

    public int getLife() {
        return life;
    }
    public void setLife(int att){
        life-=att;
    }

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        alien.type = type;
    }

    }


