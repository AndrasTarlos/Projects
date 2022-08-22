package view;


import employees.HRPerson;
import fascades.Fascade;
import log.UserAction;
import utils.Menu;
import view.buttons.AddButton;

import view.components.PersonInfo;
import view.components.PersonOverview;
import view.buttons.DeleteButton;
import view.buttons.EditButton;
import view.popups.Authorization;
import view.popups.CreateEditPerson;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;

/**
 * <h1>PersonPane</h1>
 * @author: Francesco Ryu
 * @version: 1.0
 * @date: 19.06.2022
 * <h2>Description</h2>
 * GUI for Person. Generates a panel for the specific tab in the Menu.java
 */
public class PersonPane extends JPanel {
    AddButton addButton;
    EditButton editButton;
    DeleteButton deleteButton;
    JPanel personEditPanel;
    JPanel personDetailPanel;
    JPanel checkBoxPanel;
    JCheckBox hrPersonCheckBox;
    JCheckBox administratorCheckBox;
    PersonOverview personOverview;
    PersonInfo personInfo;
    JPanel southPanel;
    JPanel buttonPanel;
    HRPerson focusedPerson;

    Fascade fascade;

    /**
     * Advanced constructor
     */
    public PersonPane() {
        fascade = Menu.fascade;

        hrPersonCheckBox = new JCheckBox("HR-Mitarbeiter ");
        hrPersonCheckBox.setFocusable(false);
        hrPersonCheckBox.setEnabled(false);
        hrPersonCheckBox.setForeground(Color.BLACK);

        administratorCheckBox = new JCheckBox("Administrator ");
        administratorCheckBox.setFocusable(false);
        administratorCheckBox.setEnabled(false);
        administratorCheckBox.setForeground(Color.BLACK);

        checkBoxPanel = new JPanel();

        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.add(hrPersonCheckBox);
        checkBoxPanel.add(administratorCheckBox);

        personEditPanel = new JPanel();
        personEditPanel.setLayout(new BorderLayout());
        personEditPanel.setBorder(new TitledBorder("   Personen bearbeiten:   "));

        personOverview = new PersonOverview(personInfo = new PersonInfo(false), null, this,  false);
        personOverview.setPreferredSize(new Dimension(170, 0));

        personDetailPanel = new JPanel();
        personDetailPanel.setBorder(new TitledBorder("   Detail:   "));
        personDetailPanel.setLayout(new GridLayout(2, 1));
        personDetailPanel.add(personInfo);

        southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        buttonPanel = new JPanel();

        personDetailPanel.add(checkBoxPanel);

        addButton = new AddButton();
        editButton = new EditButton();
        deleteButton = new DeleteButton();

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> new CreateEditPerson(personOverview, "Create", null).setVisible(true));
        editButton.addActionListener(e -> new CreateEditPerson(personOverview, "Edit", focusedPerson).setVisible(true));
        deleteButton.addActionListener(e -> {
            fascade.deletePerson(focusedPerson);
            personOverview.updateButtons();
            try {
                Authorization.currentUser.writeLogEntry(focusedPerson, UserAction.DELETE_PERSON);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        southPanel.add(buttonPanel, BorderLayout.WEST);

        personEditPanel.add(personDetailPanel, BorderLayout.CENTER);
        personEditPanel.add(personOverview, BorderLayout.WEST);

        this.setLayout(new BorderLayout());
        this.add(personEditPanel);
        this.add(southPanel, BorderLayout.SOUTH);
        this.setSize(500, 500);
        this.setVisible(true);
    }

    /**
     * Updates the content of check box
     * @param person the checkbox get the information from
     */
    public void updateCheckBox(HRPerson person) {
        setFocusedPerson(person);

        hrPersonCheckBox.setSelected(false);
        administratorCheckBox.setSelected(false);
        switch (person.getModus()) {
            case 1 -> hrPersonCheckBox.setSelected(true);
            case 2 -> administratorCheckBox.setSelected(true);
        }
    }

    /**
     * Sets the currently focused person
     * @param person HRPerson
     */
    public void setFocusedPerson(HRPerson person) {
        this.focusedPerson = person;
    }

    /**
     * Getter of PersonOverview
     * @return PersonOverview object
     */
    public PersonOverview getPersonOverview() {
        return personOverview;
    }

}
