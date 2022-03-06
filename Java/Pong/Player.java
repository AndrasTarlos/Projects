package PongGame;

public class Player {
    private String userName = "";
    private int score = 0;
    private int xPos;
    private int yPos;
    private int velocity;
    private static final int width = 6;
    private static final int height = 50;

    Player(String userName) {
        this.userName = userName;
    }

    public void spawn(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void moveUp() {
        if (yPos >= 0) {
            yPos -= 5;
        }
    }

    public void moveDown() {
        if (yPos <= 505) {
            yPos += 5;
        }
    }

    public void addScore() {
        score++;
    }

    public boolean isInHitbox(int ballXPos, int ballYPos, int ballHeightLength) {
        if (ballXPos + ballHeightLength >= xPos && ballXPos <= xPos && ballYPos + ballHeightLength >= yPos && ballYPos <= yPos + height) {
            return true;
        }
        return false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

