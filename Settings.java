import javax.swing.*;
import java.awt.*;

public class Settings extends JPanel {
    static final int SCREEN_WIDTH = 300;
    static final int SCREEN_HEIGHT = 200;
    /** Stroke size. it is recommended to set it to 1 for better view */
    protected int strokeSize = 1;
    /** Color of shadow */
    protected Color shadowColor = Color.black;
    /** Sets if it drops shadow */
    protected boolean shady = true;
    /** Sets if it has an High Quality view */
    protected boolean highQuality = true;
    /** Double values for Horizontal and Vertical radius of corner arcs */
    protected Dimension arcs = new Dimension(20, 20);
    /** Distance between shadow border and opaque panel border */
    protected int shadowGap = 5;
    /** The offset of shadow.  */
    protected int shadowOffset = 4;
    /** The transparency value of shadow. ( 0 - 255) */
    protected int shadowAlpha = 150;
    GamePanel gamePanel;
    Label labelSkin;
    JButton colourButton;

    Settings(GamePanel gamePanel, int mainPanelWidth, int mainPanelHeight) {
        this.gamePanel = gamePanel;
        labelSkin = new Label("Skin: ");
        labelSkin.setFont(new Font("Ink Free", Font.BOLD, 20));
        labelSkin.setBounds(50, 30, 50, 30);

        colourButton = new JButton();
        colourButton.setBackground(gamePanel.colourSnake);
        colourButton.setBounds(110, 28, 36, 36);
        colourButton.addActionListener(gamePanel);
        colourButton.setFocusable(false);
        colourButton.setVisible(true);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBounds(mainPanelWidth / 2 - SCREEN_WIDTH / 2,mainPanelHeight / 2 - SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setLayout(null);
        this.setBackground(Color.white);
        this.add(labelSkin);
        this.add(colourButton);
        this.setOpaque(false);
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;
        Color shadowColorA = new Color(shadowColor.getRed(),
                shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        //Sets antialiasing if HQ.
        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        // Draws shadow borders if any.
        if (shady) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(
                    shadowOffset,// X position
                    shadowOffset,// Y position
                    width - strokeSize - shadowOffset, // width
                    height - strokeSize - shadowOffset, // height
                    arcs.width, arcs.height);// arc Dimension
        } else {
            shadowGap = 1;
        }

        //Draws the rounded opaque panel with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowGap,
                height - shadowGap, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.setStroke(new BasicStroke(strokeSize));
        graphics.drawRoundRect(0, 0, width - shadowGap,
                height - shadowGap, arcs.width, arcs.height);

        //Sets strokes to default, is better.
        graphics.setStroke(new BasicStroke());
    }
}


