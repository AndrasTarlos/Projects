package PongGame;

public class Ball {

    private int xPos;
    private int yPos;
    private double xVelocity;
    private double yVelocity;
    private int heightLength = 16;

    private int screenWidth;
    private int screenHeight;

    Ball (int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void spawn(int xPos, int yPos, double xVelocity, double yVelocity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public void accelerateXVelocity() {
        if (xVelocity > 0) {
            xVelocity += 0.1;
        } else {
            xVelocity -= 0.1;
        }
    }

    public void changeYToOpposite() {
        this.yVelocity *= (-1);
        System.out.println(yVelocity);
    }

    public void changeXToOpposite() {
        this.xVelocity *= (-1);
        System.out.println(yVelocity);

    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = (int) xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = (int) yPos;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public int getHeightLength() {
        return heightLength;
    }
}
