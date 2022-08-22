package view.components;

import company.Department;
import company.Team;
import employees.HRPerson;
import employees.JobFunction;
import employees.Participation;
import fascades.Fascade;
import log.UserAction;
import view.popups.Authorization;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.IOException;

/**
 * <h1>PersonAssignmentSettings</h1>
 * @author: Andras Tarlos, Francesco Ryu
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * This is a very similar class to the PersonAssignmentSettings, but with different
 * functions and action listeners. Here, you can see all the information about
 * the members of the company.
 */
public class PersonAssignmentSettings extends JPanel {

    private final JComboBox<String> departmentComboBox;
    private final JComboBox<String> functionComboBox;
    private final JComboBox<String> teamsComboBox;

    private final Fascade fascade;
    private HRPerson focusedPerson;

    /**
     * The constructor of the PersonAssignmentSettings class
     * @param enableComboBoxes a boolean value (if enabled you can change the values of the combo boxes
     */
    public PersonAssignmentSettings(boolean enableComboBoxes) {
        this.setLayout(new BorderLayout());
        fascade = utils.Menu.fascade;

        // Initialize the panels needed
        JPanel labelPanel = new JPanel();
        JPanel selectionPanel = new JPanel();

        JLabel department = new JLabel("Abteilung:");
        department.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel function = new JLabel("Funktion:");
        function.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel teams = new JLabel("Team:");
        teams.setBorder(new EmptyBorder(5, 0, 0, 0));

        // create the 3 combo boxes and add action listeners to them
        departmentComboBox = new JComboBox<>();
        departmentComboBox.setPreferredSize(new Dimension(224, 20));
        departmentComboBox.setFocusable(false);
        departmentComboBox.addActionListener(e -> {
            // if there is a person selected from the person list and the department is
            // changed, change the selected department in the backend (model class) as well
            if (focusedPerson != null) {
                fascade.switchPersonDepartmentTo(fascade.getSearchedDepartment((String) departmentComboBox.getSelectedItem()), focusedPerson);
                writeLogEntry();
            }
        });

        functionComboBox = new JComboBox<>();
        functionComboBox.setPreferredSize(new Dimension(224, 20));
        functionComboBox.setFocusable(false);
        functionComboBox.addActionListener(e -> {
            // if there is a person selected from the person list and the job function is
            // changed, change the selected job function in the backend (model class) as well
            if (focusedPerson != null) {
                fascade.setJobFunctionOfPerson(focusedPerson, (String) functionComboBox.getSelectedItem());
                writeLogEntry();
            }

        });

        teamsComboBox = new JComboBox<>();
        teamsComboBox.setPreferredSize(new Dimension(224, 20));
        teamsComboBox.setFocusable(false);
        teamsComboBox.addActionListener(e -> {
            // if there is a person selected from the person list and the department is
            // changed, change the selected department in the backend (model class) as well
            if (focusedPerson != null) {
                fascade.setTeamOfPerson(focusedPerson, (String) teamsComboBox.getSelectedItem());
                focusedPerson.getParticipation().setTeam(fascade.getSearchedTeam((String) teamsComboBox.getSelectedItem()));
                writeLogEntry();
            }
        });

        // disable the combo boxes if the parameter given in the constructor is false
        if (!enableComboBoxes) {
            departmentComboBox.setEnabled(false);
            departmentComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public void paint(Graphics g) {
                    setForeground(Color.BLACK);
                    super.paint(g);
                }
            });

            functionComboBox.setEnabled(false);
            functionComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public void paint(Graphics g) {
                    setForeground(Color.BLACK);
                    super.paint(g);
                }
            });

            teamsComboBox.setEnabled(false);
            teamsComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public void paint(Graphics g) {
                    setForeground(Color.BLACK);
                    super.paint(g);
                }
            });
        }

        selectionPanel.add(departmentComboBox);
        selectionPanel.add(functionComboBox);
        selectionPanel.add(teamsComboBox);

        labelPanel.add(department);
        labelPanel.add(function);
        labelPanel.add(teams);

        labelPanel.setPreferredSize(new Dimension(95, 0));

        // load the data in the combo boxes
        loadComboBoxData();

        this.add(labelPanel, BorderLayout.WEST);
        this.add(selectionPanel, BorderLayout.CENTER);
    }

    /**
     * Gets all the data from the fascade and inserts them in
     * the combo boxes
     */
    public void loadComboBoxData() {
        for (Team t: fascade.getTeams()) {
            teamsComboBox.addItem(t.getDesignation());
        }
        for (JobFunction f: fascade.getJobFunctions()) {
            functionComboBox.addItem(f.getDesignation());
        }
        for (Department d: fascade.getAllDepartment()) {
            departmentComboBox.addItem(d.getName());
        }
    }

    /**
     * If the user selects another person from the member list,
     * the selected items (Abteilung, Funktion, Team) in the combo boxes are changed to the
     * ones the person is in.
     * @param person a member
     */
    public void updateComboBox(HRPerson person) {
        focusedPerson = person;
        Participation p = person.getParticipation();
        teamsComboBox.setSelectedItem(p.getTeam().getDesignation());
        functionComboBox.setSelectedItem(p.getFunction().getDesignation());
        departmentComboBox.setSelectedItem(person.getDepartmentName());
    }

    /**
     * Writes a log entry
     */
    public void writeLogEntry() {
        try {
            if (Authorization.currentUser != null)
                Authorization.currentUser.writeLogEntry(focusedPerson, UserAction.SET_ASSIGNMENT);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
