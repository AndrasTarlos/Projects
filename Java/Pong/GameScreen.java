package PongGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

public class GameScreen extends JPanel implements ActionListener {

    private Player player2;
    private Player player1;
    private Ball ball;
    private Timer timer;

    private static final int screenWidth = 800;
    private static final int screenHeight = 562;

    private int p1Score = 0, p2Score = 0;

    GameScreen(String userNameP1, String userNameP2) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(null);

        player1 = new Player(userNameP1);
        player2 = new Player(userNameP2);
        ball = new Ball(screenWidth, screenHeight);

        timer = new Timer(3, this);

        startGame();

        this.setBackground(Color.BLACK);
        this.setOpaque(true);

    }

    public void startGame() {
        spawn();
        timer.start();
    }

    public void spawn() {
        // Generate random position for the ball
        int randomXStartPos = ThreadLocalRandom.current().nextInt(0, screenWidth);
        int randomYStartPos;
        double xVelocity = 1;
        double yVelocity = 1;

        if (getRandomBoolean()) {
            randomYStartPos = -20;
        } else {
            randomYStartPos = screenHeight - ball.getHeightLength();
            yVelocity *= (-1);
        }

        if (randomXStartPos > (screenWidth - ball.getHeightLength()) / 2) {
            xVelocity *= (-1);
        }

        player1.spawn(40, 250);
        player2.spawn(650, 250);
        ball.spawn(randomXStartPos, randomYStartPos, xVelocity, yVelocity);
    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    /**
     * This paint method draws a line in the middle of the screen
     * @param g for graphics
     */
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        super.paint(g);
        g2D.setPaint(Color.WHITE);

        // Draws a line in the center
        g2D.setStroke(new BasicStroke(6));
        int yPos = 0;
        for (int i = 0; i < 29; i++) {
            if (i % 2 == 0)
                g2D.drawLine(350, yPos, 350, yPos + 20);
            yPos += 20;
        }

        // Draw the ball
        g2D.fillOval(ball.getxPos(), ball.getyPos(), ball.getHeightLength(), ball.getHeightLength());
        // Draw player 1
        g2D.fillRect(player1.getxPos(), player1.getyPos(), player1.getWidth(), player1.getHeight());
        // Draw player 2
        g2D.fillRect(player2.getxPos(), player2.getyPos(), player2.getWidth(), player2.getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // If the score of a player change start the game again
        if (p1Score != player1.getScore() || p2Score != player2.getScore()) {
            p1Score = player1.getScore();
            p2Score = player2.getScore();
            spawn();
        }
        // Add a score to player 1
        if (ball.getxPos() > screenWidth) {
            player1.addScore();
        }
        // Add a score to player 2
        if (ball.getxPos() + ball.getHeightLength() < 0) {
            player2.addScore();
        }
        // Change y velocity if ball touches the walls
        if (ball.getyPos() >= screenHeight - ball.getHeightLength() && ball.getyVelocity() > 0|| ball.getyPos() < 0 && ball.getyVelocity() < 0) {
            ball.changeYToOpposite();
        }
        // Change x velocity if ball
        if (player1.isInHitbox(ball.getxPos(), ball.getyPos(), ball.getHeightLength()) || player2.isInHitbox(ball.getxPos(), ball.getyPos(), ball.getHeightLength())) {
            ball.changeXToOpposite();
            ball.accelerateXVelocity();

        }

        // Animate the ball
        ball.setyPos(ball.getyPos() + (ball.getyVelocity()));
        ball.setxPos(ball.getxPos() + (ball.getxVelocity()));
        repaint();
    }

    public void setPlayerOneName(String name) {
        player1.setUserName(name);
    }

    public void setPlayerTwoName(String name) {
        player2.setUserName(name);
    }

    public int getPlayerOneScore() {
        return player1.getScore();
    }

    public int getPlayerTwoScore() {
        return player2.getScore();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }


}
