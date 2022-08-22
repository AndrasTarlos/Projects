package view.components;

import company.Department;
import company.Team;
import employees.JobFunction;
import fascades.Fascade;
import utils.Menu;
import view.buttons.AddButton;
import view.buttons.DeleteButton;
import view.buttons.EditButton;
import view.popups.CreateFunction;
import view.popups.EditFunction;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * <h1>DataInfoPanel</h1>
 * @author: Francesco Ryu / Andras Tarlos
 * @version: 9.0
 * @date: 21.06.2022
 * <h2>Description</h2>
 * Creates GUI to display the 3 editable scrollpanes for function, department and teams.
 * You can add, delete and edit the mentioned lists.
 */

public class DataInfoPanel extends JPanel {
    private JPanel contentPanel;
    private JScrollPane jScrollPane;
    private JPanel buttonPanel;
    private JPanel buttonScrollPanePanel;
    private JPanel placeHolder;
    private JPanel labelPanel;
    private JLabel label;
    private Object[] itemList;
    private String labelName;
    private AddButton addButton;
    private EditButton editButton;
    private DeleteButton deleteButton;
    private Fascade fascade;
    CreateFunction createFunction;
    EditFunction editFunction;
    private String focusedItem;

    public DataInfoPanel(String labelName) {
        fascade = utils.Menu.fascade;
        label = new JLabel(labelName);

        setLabelName(labelName);

        // Initialize all buttons
        addButton = new AddButton();
        editButton = new EditButton();
        deleteButton = new DeleteButton();

        // Open create-edit-delete function dynamically depending on which button was pressed
        switch (labelName) {
            case "Abteilung:" -> {
                addButton.addActionListener(e -> {
                    createFunction = new CreateFunction("Abteilung", this);
                });
                editButton.addActionListener(e -> {
                    if (focusedItem != null)
                        editFunction = new EditFunction("Abteilung", focusedItem, this);
                    updateButtons();
                });
                deleteButton.addActionListener(e -> {
                    if (focusedItem != null) {
                        Menu.fascade.deleteDepartment(focusedItem);
                        focusedItem = null;
                    }
                    updateButtons();
                });
            }
            case "Funktionen:" -> {
                addButton.addActionListener(e -> {
                    createFunction = new CreateFunction("Funktion", this);
                });
                editButton.addActionListener(e -> {
                    if (focusedItem != null)
                        editFunction = new EditFunction("Funktion", focusedItem, this);
                });
                deleteButton.addActionListener(e -> {
                    if (focusedItem != null) {
                        Menu.fascade.deleteJobFunction(focusedItem);
                        focusedItem = null;
                    }
                    updateButtons();
                });
            }
            case "Teams:" -> {
                addButton.addActionListener(e -> {
                    createFunction = new CreateFunction("Team", this);
                });
                editButton.addActionListener(e -> {
                    if (focusedItem != null)
                        editFunction = new EditFunction("Team", focusedItem, this);
                });
                deleteButton.addActionListener(e -> {
                    if (focusedItem != null) {
                        Menu.fascade.deleteTeam(focusedItem);
                        focusedItem = null;
                    }
                    updateButtons();
                });
            }
        }

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        jScrollPane = new JScrollPane(contentPanel);
        jScrollPane.setPreferredSize(new Dimension(200, 140));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add buttons method
        addButtons();

        buttonScrollPanePanel = new JPanel();
        buttonScrollPanePanel.add(jScrollPane);
        buttonScrollPanePanel.add(buttonPanel);
        buttonScrollPanePanel.setPreferredSize(new Dimension(300, 195));

        placeHolder = new JPanel();

        labelPanel = new JPanel();
        labelPanel.add(label, BorderLayout.CENTER);
        label.setPreferredSize(new Dimension(150, 150));

        this.add(labelPanel);
        this.add(buttonScrollPanePanel);
        this.setBorder(new LineBorder(Color.BLACK));
    }

    /**
     * Adds the buttons to the scrollPanes panel
     */
    public void addButtons() {
        // Save the individual lists in an object array
        switch (labelName) {
            case "Abteilung:" -> itemList = fascade.getAllDepartment().toArray();
            case "Funktionen:" -> itemList = fascade.getJobFunctions().toArray();
            case "Teams:" -> itemList = fascade.getTeams().toArray();
        }

        for (int i = 0; i < itemList.length; i++) {
            String name = null;
            // Type cast the objects into the right type
            switch (labelName) {
                case "Abteilung:" -> name = ((Department) itemList[i]).getName();
                case "Funktionen:" -> name = ((JobFunction) itemList[i]).getDesignation();
                case "Teams:" -> name = ((Team) itemList[i]).getDesignation();
            }

            JButton button = new JButton(name);
            button.setMaximumSize(new Dimension(180, 30));
            button.setPreferredSize(new Dimension(180, 30));

            button.setBorder(null);
            button.setBorderPainted(false);
            button.setBackground(new Color(246, 245, 245, 255));
            button.setFocusable(false);

            button.addActionListener(e -> focusedItem = e.getActionCommand());
            contentPanel.add(button);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Updates and refreshes the buttons
     */
    public void updateButtons() {
        contentPanel.removeAll();
        addButtons();
    }

    /**
     * Sets the name of the label
     * @param labelName String
     */
    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
