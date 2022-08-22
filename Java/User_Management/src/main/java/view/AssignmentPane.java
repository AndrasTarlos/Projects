package view;

import view.components.PersonAssignmentSettings;
import view.components.PersonInfo;
import view.components.PersonOverview;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * <h1>AssignmentPane</h1>
 * @author: Francesco Ryu
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * GUI for assigning person. Generates a tab in the Menu.java
 */

public class AssignmentPane extends JPanel {
    private JPanel personEditPanel;
    private PersonOverview personOverviewPanel;
    private JPanel personDetailPanel;
    private PersonInfo personInfo;
    private PersonAssignmentSettings personAssignmentPanel;
    JLabel personEditLabel;

    public AssignmentPane() {
        this.setLayout(new BorderLayout());

        GridLayout personDetailLayout = new GridLayout(2, 0);


        //Generates imported components
        personEditPanel = new JPanel();
        personInfo = new PersonInfo(false);
        personAssignmentPanel = new PersonAssignmentSettings(true);
        personOverviewPanel = new PersonOverview(personInfo, personAssignmentPanel, null,  false);
        personDetailPanel = new JPanel();

        personEditPanel.setBorder(new TitledBorder("   Person zuordnen:   "));
        personEditPanel.setLayout(new BorderLayout());
        personEditLabel = new JLabel("   Personen bearbeiten:   ");
        personEditPanel.add(personOverviewPanel, BorderLayout.WEST);

        personDetailPanel.setBorder(new TitledBorder("   Detail:   "));
        personDetailPanel.setLayout(personDetailLayout);
        personDetailPanel.add(personInfo);
        personDetailPanel.add(personAssignmentPanel);

        personEditPanel.add(personDetailPanel, BorderLayout.CENTER);
        this.add(personEditPanel);
        this.setVisible(true);
    }

    /**
     * Getter of PersonOverview
     * @return PersonOverview object
     */
    public PersonOverview getPersonOverviewPanel() {
        return personOverviewPanel;
    }
}
