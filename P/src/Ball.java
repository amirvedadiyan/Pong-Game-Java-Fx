import java.util.Random;

public class Ball {
    private double vx;
    private double vy;

    public Ball(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public Ball() {
        Random random = new Random();
        this.vx = random.nextDouble() * 100 % 7 + 0.5;
        this.vy = random.nextDouble() * 100 % 7 + 0.5;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

}
