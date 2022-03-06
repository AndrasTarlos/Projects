package PongGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    JLabel titleLabel, usernames, errorMessage;
    JButton button;
    JTextField userName1Input, userName2Input;

    Login() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Login");
        this.setSize(new Dimension(400, 300));
        this.setBackground(Color.gray);
        this.setLayout(null);

        titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("consolas", Font.PLAIN, 35));
        titleLabel.setBounds(140, 30, 100, 50);

        usernames = new JLabel("Username Player 1 | Username Player 2");
        usernames.setBounds(65, 85, 350, 40);
        usernames.setFont(new Font("consolas", Font.PLAIN, 12));

        errorMessage = new JLabel();
        errorMessage.setFont(new Font("consolas", Font.PLAIN, 10));
        errorMessage.setBounds(70, 70, 300, 40);
        errorMessage.setForeground(Color.RED);
        errorMessage.setVisible(true);

        button = new JButton("Start Game");
        button.setBounds(140, 180, 100, 30);
        button.setBackground(Color.green);
        button.setOpaque(true);
        button.setFocusable(false);
        button.addActionListener(this);

        userName1Input = new JTextField();
        userName1Input.setBounds(80, 120, 100, 40);
        userName1Input.setPreferredSize(new Dimension(100, 40));
        userName1Input.setFont(new Font("consolas", Font.PLAIN, 20));

        userName2Input = new JTextField();
        userName2Input.setBounds(200, 120, 100, 40);
        userName2Input.setPreferredSize(new Dimension(100, 40));
        userName2Input.setFont(new Font("consolas", Font.PLAIN, 20));

        this.add(userName1Input);
        this.add(userName2Input);
        this.add(titleLabel);
        this.add(usernames);
        this.add(button);
        this.add(errorMessage);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            if (userName1Input.getText().length() <= 5 && userName1Input.getText().length() >= 2 && userName2Input.getText().length() <= 5 && userName2Input.getText().length() >= 2) {
                button.setEnabled(false);
                this.dispose();
                new Game(userName1Input.getText(), userName2Input.getText());
            } else if (userName1Input.getText().length() <= 2 && userName2Input.getText().length() <= 2) {
                errorMessage.setText("*Usernames have to contain at least 2 chars");
                errorMessage.setVisible(true);
            } else {
                errorMessage.setText("*Usernames can only contain five letters!");
                errorMessage.setVisible(true);
            }
        }
    }
}
