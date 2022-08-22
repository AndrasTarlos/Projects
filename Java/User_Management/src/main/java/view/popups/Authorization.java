package view.popups;

import employees.HRPerson;
import fascades.Fascade;
import utils.Menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

/**
 * <h1>Authorization</h1>
 * @author: Francesco Ryu / Andras Tarlos
 * @version: 3.0
 * @date: 21.06.2022
 * <h2>Description</h2>
 * Creates the login GUI popup for HRPerson and admin
 * to acquire higher privileges like editing, adding
 * and deleting content.
 */

public class Authorization extends JDialog {
    JPanel labelPanel;
    JPanel interactionPanel;
    JPanel buttonPanel;
    JPanel mainPanel;

    JLabel nameLabel;
    JLabel codeLabel;

    JPasswordField inputCode;
    JComboBox<Object> selectPerson;

    JButton quitButton;
    JButton continueButton;

    private boolean loggedIn = false;

    public static HRPerson currentUser;

    /**
     * Advanced constructor
     * @param menu object
     * @param tabbedPane object
     */
    public Authorization(Menu menu, JTabbedPane tabbedPane) {
        super(menu, true);

        Fascade fascade = Menu.fascade;

        EmptyBorder emptyBorder = new EmptyBorder(5, 5, 5, 5);

        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        nameLabel = new JLabel("Name:");
        codeLabel = new JLabel("Code:");
        codeLabel.setBorder(new EmptyBorder(20, 0, 0, 0));
        labelPanel.add(nameLabel);
        labelPanel.add(codeLabel);
        labelPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        interactionPanel = new JPanel();
        interactionPanel.setLayout(new BoxLayout(interactionPanel, BoxLayout.Y_AXIS));
        selectPerson = new JComboBox<>();
        selectPerson.setBorder(new EmptyBorder(5, 0, 10, 0));
        interactionPanel.add(selectPerson);
        addComboBoxContent();
        inputCode = new JPasswordField();


        interactionPanel.add(inputCode);
        interactionPanel.setBorder(new EmptyBorder(5, 20, 20, 20));


        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        quitButton = new JButton(" Abbrechen ");
        continueButton = new JButton(" Weiter ");
        buttonPanel.add(quitButton, BorderLayout.WEST);
        buttonPanel.add(continueButton, BorderLayout.EAST);
        buttonPanel.setBorder(emptyBorder);

        continueButton.addActionListener(e -> {
            currentUser = fascade.getPersonByFullName(Objects.requireNonNull(selectPerson.getSelectedItem()).toString());


            if (inputCode.getText().equals(fascade.getPersonsPassword(currentUser))) {
                setVisible(false);
                loggedIn = true;
                fascade.setHRPersonLoggedIn(true);
                JOptionPane.showMessageDialog(this, "Login erfolgreich");
            }
            if (!inputCode.getText().equals(fascade.getPersonsPassword(currentUser))) {
                tabbedPane.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "Ungültiger Code und/oder Name");
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(0);
                dispose();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!loggedIn) {
                    tabbedPane.setSelectedIndex(0);
                }
            }
        });

        this.setTitle("Authentifizierung");
        this.add(labelPanel, BorderLayout.WEST);
        this.add(interactionPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setResizable(false);
        this.setSize(300, 150);
        this.setLocation(250, 250);
        this.setVisible(true);
    }

    /**
     * Fills the combo box with HRPerson or / and Administrators
     */
    private void addComboBoxContent() {
        for (HRPerson p: Menu.fascade.getAllHRPerson()) {
            selectPerson.addItem(p.getFullName());
        }
    }
}
