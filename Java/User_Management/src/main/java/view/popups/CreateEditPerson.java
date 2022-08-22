package view.popups;

import employees.HRPerson;
import log.UserAction;
import view.components.PersonInfo;
import view.components.PersonOverview;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * <h1>CreateEditPerson</h1>
 * @author: Andras Tarlos / Francesco Ryu
 * @version: 4.0
 * @date: 20.06.2022
 * <h2>Description</h2>
 * Creates a dynamic GUI popup that is able to
 * either create a new member/person/user or edit
 * an already existing member.
 */

public class CreateEditPerson extends JDialog {
    PersonInfo personInfoPanel;
    JCheckBox hrPersonCheckBox;
    JCheckBox administratorCheckBox;


    JPanel checkBoxPanel;
    JPanel buttonPanel;

    JPanel pwdPanel;

    JLabel pwdLabel;

    JTextField pwdTextField;
    JButton quitButton;
    JButton saveButton;

    /**
     * Advanced constructor
     * @param personOverview object to update it
     * @param type String (Create / Edit)
     * @param focusedPerson an HRPerson object
     */
    public CreateEditPerson(PersonOverview personOverview, String type, HRPerson focusedPerson) {
        personInfoPanel = new PersonInfo(true);

        pwdPanel = new JPanel();
        pwdLabel = new JLabel();
        pwdTextField = new JTextField();
        pwdTextField.setEnabled(false);
        pwdTextField.setColumns(15);
        pwdPanel.add(pwdLabel);
        pwdPanel.add(pwdTextField);
        pwdPanel.setVisible(true);

        hrPersonCheckBox = new JCheckBox("HR-Mitarbeiter");
            hrPersonCheckBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    pwdTextField.setEnabled(true);
                }
                else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    pwdTextField.setEnabled(false);
                }
            });

        administratorCheckBox = new JCheckBox("Administrator");
        administratorCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                pwdTextField.setEnabled(true);
            }
            else if (e.getStateChange() == ItemEvent.DESELECTED) {
                pwdTextField.setEnabled(false);
            }
        });

        checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.add(hrPersonCheckBox, BorderLayout.NORTH);
        checkBoxPanel.add(administratorCheckBox, BorderLayout.CENTER);
        checkBoxPanel.add(pwdPanel, BorderLayout.SOUTH);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        quitButton = new JButton(" Abbrechen ");
        quitButton.addActionListener(e -> this.dispose());
        saveButton = new JButton(" Speichern ");
        buttonPanel.add(saveButton, BorderLayout.EAST);
        buttonPanel.add(quitButton, BorderLayout.WEST);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        saveButton.addActionListener(e -> {
            // Only accept the "Speichern" button, if the user has entered a name
            if (!personInfoPanel.getName().equals("") && focusedPerson == null && type.equals("Create")) {
                HRPerson p = new HRPerson();

                // Set the modus or / and the password if needed
                if (administratorCheckBox.isSelected()) {
                    p.setModus(2);
                    p.setPwd(pwdTextField.getText());
                } else if (hrPersonCheckBox.isSelected()) {
                    p.setModus(1);
                    p.setPwd(pwdTextField.getText());
                } else {
                    p.setModus(0);
                    p.setPwd(null);
                }
                // Set the name of the new person
                String[] nameSplit = personInfoPanel.getName().split(" ");
                p.setFirstName(nameSplit[0]);
                if (nameSplit.length > 1)
                    p.setLastName(nameSplit[1]);

                utils.Menu.fascade.createPerson(p);
                try {
                    Authorization.currentUser.writeLogEntry(p, UserAction.CREATE_PERSON);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                this.dispose();
            }
            if (type.equals("Edit")) {
                // Change the name of the person
                String[] nameSplit = personInfoPanel.getName().split(" ");
                focusedPerson.setFirstName(nameSplit[0]);
                if (nameSplit.length > 1)
                    focusedPerson.setLastName(nameSplit[1]);
                // Change the modus or / and the password if needed
                if (administratorCheckBox.isSelected()) {
                    focusedPerson.setModus(2);
                    focusedPerson.setPwd(pwdTextField.getText());
                } else if (hrPersonCheckBox.isSelected()) {
                    focusedPerson.setModus(1);
                    focusedPerson.setPwd(pwdTextField.getText());
                } else {
                    focusedPerson.setModus(0);
                    focusedPerson.setPwd(null);
                }
                try {
                    Authorization.currentUser.writeLogEntry(focusedPerson, UserAction.CHANGE_VALUE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                this.dispose();
            }
            personOverview.updateButtons();
        });

        // Differences between edit and create
        if (type.equals("Create")) {
            this.setTitle("Person erfassen");
            pwdLabel.setText("Passwort hinzufügen:");
        } else if (type.equals("Edit")){
            this.setTitle("Person bearbeiten");
            pwdLabel.setText("Passwort bearbeiten:");
        }

        // If bearbeiten is active
        if (focusedPerson != null) {
            personInfoPanel.setName(focusedPerson.getFirstName() + " " + focusedPerson.getLastName());
            if (focusedPerson.isHRPerson()) {
                hrPersonCheckBox.setSelected(true);
                pwdTextField.setText(focusedPerson.getPwd());
            }
            if (focusedPerson.isAdmin()) {
                administratorCheckBox.setSelected(true);
                pwdTextField.setText(focusedPerson.getPwd());
            }
            this.dispose();
        }

        this.setLayout(new BorderLayout());
        this.add(personInfoPanel, BorderLayout.NORTH);
        this.add(checkBoxPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setResizable(false);
        this.setSize(400, 400);
        this.setLocation(250, 250);
    }
}
