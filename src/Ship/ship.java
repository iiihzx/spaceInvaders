public class ship{
    public static double tx;
    int cur_life=100;
    int life=3;

    public static void setX(double x) {
        tx = x;
    }

    public static double getX() {
        return tx;
    }

    public void setLife(int life1) {
        life = life1;
    }

    public int getLife() {
        return life;
    }
}