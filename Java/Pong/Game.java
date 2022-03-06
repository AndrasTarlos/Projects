package PongGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame implements ActionListener {

    private Login login;

    private final JLabel player1Label;
    private final JLabel player2Label;
    private static JLabel title;

    private static JButton exitButton;

    private final ImageIcon imageIcon = new ImageIcon("C:\\Users\\andra\\OneDrive - Bildungszentrum Zürichsee\\Coding\\Java\\MyProjects\\PingPong\\src\\PongGame\\ball.png");

    private static JPanel bottom;
    private static JPanel stats;
    private static GameScreen gameScreen;

    private KeyMovement p1KeyListener, p2KeyListener;
    private Thread threadP1;
    private Thread threadP2;

    private Timer timer;
    private int currentPlayer1Score = 0;
    private int currentPlayer2Score = 0;

    Game(String userNameP1, String userNameP2) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Pong");
        this.setSize(new Dimension(700, 690));
        this.setIconImage(imageIcon.getImage());
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        stats = new JPanel();
        bottom = new JPanel();
        gameScreen = new GameScreen(userNameP1, userNameP2);

        p1KeyListener = new KeyMovement(gameScreen.getPlayer1(),false,this);
        p2KeyListener = new KeyMovement(gameScreen.getPlayer2(), true, this);
        threadP1 = new Thread(p1KeyListener);
        threadP2 = new Thread(p2KeyListener);

        timer = new Timer(50, this);

        title = new JLabel();
        player1Label = new JLabel();
        player2Label = new JLabel();

        exitButton = new JButton();

        title.setText("<HTML><U>Pong</U></HTML>");
        title.setFont(new Font("MV Boli", Font.PLAIN, 25));
        title.setBounds(320, 10, 80, 30);

        player1Label.setText(gameScreen.getPlayer1().getUserName() + " " + currentPlayer1Score);
        player1Label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        player1Label.setBounds(100, 10, 80, 30);

        player2Label.setText(currentPlayer2Score + " " + gameScreen.getPlayer2().getUserName());
        player2Label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        player2Label.setBounds(520, 10, 80, 30);

        stats.setPreferredSize(new Dimension(0, 50));
        stats.setLayout(null);
        stats.setBackground(Color.LIGHT_GRAY);
        stats.setOpaque(true);

        stats.add(player1Label);
        stats.add(title);
        stats.add(player2Label);

        bottom.setPreferredSize(new Dimension(0, 40));
        bottom.setLayout(null);
        bottom.setBackground(Color.LIGHT_GRAY);
        bottom.setOpaque(true);

        exitButton.setText("Exit");
        exitButton.setBounds(310, 5, 80, 30);
        exitButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
        exitButton.setForeground(Color.LIGHT_GRAY);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.setBackground(Color.BLACK);
        exitButton.setOpaque(true);
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);

        bottom.add(exitButton);
        threadP1.start();
        threadP2.start();
        timer.start();

        this.add(stats, BorderLayout.NORTH);
        this.add(bottom, BorderLayout.SOUTH);
        this.add(gameScreen, BorderLayout.CENTER);
        this.setResizable(false);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // If score of player 1 changes change the stat
        if (gameScreen.getPlayer1().getScore() != currentPlayer1Score) {
            currentPlayer1Score++;
            player1Label.setText(gameScreen.getPlayer1().getUserName() + " " + currentPlayer1Score);
        }
        // If score of player 2 changes change the stat
        if (gameScreen.getPlayer2().getScore() != currentPlayer2Score) {
            currentPlayer2Score++;
            player2Label.setText(currentPlayer2Score + " " + gameScreen.getPlayer2().getUserName());
        }

        if (e.getSource() == exitButton) {
            dispose();
            System.exit(0);
        }
    }
}
