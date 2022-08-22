package view.buttons;

import utils.DatahandlerJSON;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * <h1>DeleteButton</h1>
 * @author: Francesco Ryu
 * @date: 15.06.2022
 * @version: 1.0
 * <h2>Description</h2>
 * This class is extended by JButton and used to
 * delete items/elements from a list. The button is
 * recognisable by the x sign as its icon in its middle.
 */
public class DeleteButton extends JButton {
    private static URI imgPath;

    static {
        try {
            imgPath = Objects.requireNonNull(DatahandlerJSON.class.getResource("../IMAGES/DeleteButton.png")).toURI();

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Advanced constructor
     */
    public DeleteButton() {
        ImageIcon addButtonPic = new ImageIcon(Paths.get(imgPath).toString());
        JLabel imgLabel = new JLabel(addButtonPic);
        this.setBackground(new Color(246, 245, 245, 255));
        this.add(imgLabel);
    }
}
