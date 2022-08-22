package view;

import view.components.PersonAssignmentFiltering;
import view.components.PersonAssignmentSettings;
import view.components.PersonInfo;
import view.components.PersonOverview;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <h1>OverviewPane</h1>
 * @author: Francesco Ryu/Andras Tarlos
 * @version: 13.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * GUI for Overview. Generates a panel for the specific tab in the Menu.java
 */
public class OverviewPane extends JPanel {
    PersonInfo personInfo;
    PersonAssignmentSettings personAssignmentPanel;
    PersonAssignmentFiltering filterAssignmentPanel;
    PersonOverview personOverview;

    JPanel personenPanel;
    JPanel personDetailPanel;
    JPanel southPanel;
    JPanel sortPanel;
    JPanel filterPanel;

    JRadioButton noneSort, AtoZSort, ZToASort;

    ButtonGroup sortBtnGroup;


    public OverviewPane() {
        GridLayout personDetailLayout = new GridLayout(2, 0);
        this.setLayout(new BorderLayout());


        // Generates imported components

        personInfo = new PersonInfo(false);
        personAssignmentPanel = new PersonAssignmentSettings(false);
        personOverview = new PersonOverview(personInfo, personAssignmentPanel, null, true);
        personenPanel = new JPanel(new BorderLayout());
        personDetailPanel = new JPanel(personDetailLayout);
        filterAssignmentPanel = new PersonAssignmentFiltering(personOverview);

        personenPanel.setBorder(new TitledBorder("   Personen:    "));

        southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.setPreferredSize(new Dimension(0, 120));

        sortPanel = new JPanel();
        sortPanel.setBorder(new TitledBorder("Sortierung:"));
        sortPanel.setPreferredSize(new Dimension(175, 0));
        sortPanel.setLayout(new BorderLayout());

        sortBtnGroup = new ButtonGroup();

        ActionListener sortActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton aButton = (AbstractButton) actionEvent.getSource();
                personOverview.sortPerson(aButton.getText());
            }
        };


        // Sort functions to display the people in desired order

        noneSort = new JRadioButton("Keine");
        noneSort.addActionListener(sortActionListener);
        noneSort.setSelected(true);
        noneSort.setFocusable(false);
        AtoZSort = new JRadioButton("A-Z");
        AtoZSort.addActionListener(sortActionListener);
        AtoZSort.setFocusable(false);
        ZToASort = new JRadioButton("Z-A");
        ZToASort.addActionListener(sortActionListener);
        ZToASort.setFocusable(false);

        sortBtnGroup.add(noneSort);
        sortBtnGroup.add(AtoZSort);
        sortBtnGroup.add(ZToASort);

        sortPanel.add(noneSort, BorderLayout.NORTH);
        sortPanel.add(AtoZSort, BorderLayout.CENTER);
        sortPanel.add(ZToASort, BorderLayout.SOUTH);

        filterPanel = new JPanel();
        filterPanel.setBorder(new TitledBorder("Filter:"));
        filterPanel.setLayout(new BorderLayout());

        JLabel department = new JLabel("Abteilung:");
        department.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel function = new JLabel("Funktion:");
        function.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel teams = new JLabel("Teams:");
        teams.setBorder(new EmptyBorder(5, 0, 0, 0));

        filterPanel.add(filterAssignmentPanel);

        southPanel.add(sortPanel, BorderLayout.WEST);
        southPanel.add(filterPanel, BorderLayout.CENTER);

        personOverview.setPreferredSize(new Dimension(170, 0));

        personDetailPanel.setBorder(new TitledBorder("  Detail: "));
        personDetailPanel.add(personInfo);
        personDetailPanel.add(personAssignmentPanel);

        personenPanel.add(personOverview, BorderLayout.WEST);
        personenPanel.add(personDetailPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(personenPanel);
    }

    /**
     * Getter of personOverview
     * @return PersonOverview
     */
    public PersonOverview getPersonOverview() {
        return personOverview;
    }
}
