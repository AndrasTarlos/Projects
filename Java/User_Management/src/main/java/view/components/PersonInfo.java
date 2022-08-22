package view.components;

import employees.Person;
import utils.DatahandlerJSON;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 * <h1>PersonInfo</h1>
 * @author: Francesco Ryu / Andras Tarlos
 * @version: 5.0
 * @date: 20.06.2022
 * <h2>Description</h2>
 * Person Info shows the name and the profile picture
 * of the selected person.
 */

public class PersonInfo extends JPanel {
    JTextField nameInputTextField;
    JLabel personNameLabel;

    JLabel imagePlaceHolder;

    private static final URI imgPath;

    static {
        try {
            imgPath = Objects.requireNonNull(DatahandlerJSON.class.getResource("../IMAGES/profile.png")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Advanced constructor
     * @param textFieldEditable boolean value
     */
    public PersonInfo(boolean textFieldEditable) {
        personNameLabel = new JLabel("  Name: ");

        nameInputTextField = new JTextField();
        nameInputTextField.setColumns(25);
        nameInputTextField.setEditable(true);

        PromptSupport.setPrompt("Vorname und Nachname eingeben", nameInputTextField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, nameInputTextField);

        ImageIcon profileImage = new ImageIcon(Paths.get(imgPath).toString());

        imagePlaceHolder = new JLabel(profileImage);
        imagePlaceHolder.setPreferredSize(new Dimension(200, 200));
        imagePlaceHolder.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));

        this.add(personNameLabel);
        this.add(nameInputTextField);
        this.add(imagePlaceHolder);
        this.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
        if (!textFieldEditable) {
            nameInputTextField.setEditable(false);
        }
        if (textFieldEditable) {
            imagePlaceHolder.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    int returnValue = jfc.showOpenDialog(null);
                    // int returnValue = jfc.showSaveDialog(null);

                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = jfc.getSelectedFile();
                        System.out.println(selectedFile.getAbsolutePath());
                    }
                }
            });
        }


    }

    /**
     * Updates the name text field with the new parameter
     * @param person a Person object
     */
    public void update(Person person) {
        nameInputTextField.setText(person.getFirstName() + " " + person.getLastName());
    }

    /**
     * Getter of the name text field content
     * @return String
     */
    public String getName() {
        return nameInputTextField.getText();
    }

    /**
     * Setter of the name text field content
     * @param s  the string that is to be this
     *           component's name
     */
    public void setName(String s) {
        nameInputTextField.setText(s);
    }
}
