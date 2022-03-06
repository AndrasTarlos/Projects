import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameFrame extends JFrame {
    Path currentRelativePath = Paths.get("");
    ImageIcon snake = new ImageIcon(currentRelativePath.toAbsolutePath().toString() + "\\src\\images\\snake.png");
    GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setIconImage(snake.getImage());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}