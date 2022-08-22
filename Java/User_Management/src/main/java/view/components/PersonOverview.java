package view.components;

import employees.HRPerson;
import exception.UnknownSortingTypeException;
import org.jetbrains.annotations.NotNull;
import fascades.Fascade;
import utils.Menu;
import utils.DatahandlerJSON;
import view.PersonPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * <h1>PersonOverview</h1>
 * @author: Francesco Ryu/Andras Tarlos
 * @version: 17.0
 * @date: 20.06.2022
 * <h2>Description</h2>
 * This class contains a JScrollPane with all the workers/persons
 * of the company.
 */

public class PersonOverview extends JPanel {
    private PersonInfo personInfo;
    private PersonAssignmentSettings personAssignmentSettings;
    private List<HRPerson> personList;
    private JScrollPane scrollPanePerson;
    private PersonPane personPane;
    private final JPanel contentPanel;
    private final Fascade fascade;
    private JPanel searchBar;
    private final JTextField searchBarTextField;

    private static final URI imgPath;

    static {
        try {
            imgPath = Objects.requireNonNull(DatahandlerJSON.class.getResource("../IMAGES/lensImage.png")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * constructor for the PersonOverview class
     * @param personInfo object of PersonInfo
     * @param personAssignmentSettings object of personAssignmentSettings
     * @param personPane object of personPane
     * @param setVisibleSearchBar if you set this true, you will see the searchBar like in this tab
     */

    public PersonOverview(PersonInfo personInfo, PersonAssignmentSettings personAssignmentSettings, PersonPane personPane, boolean setVisibleSearchBar) {
        this.setLayout(new BorderLayout());
        personList = new ArrayList<>();
        fascade = Menu.fascade;
        personList = fascade.getAllPerson();

        setPersonInfoPanel(personInfo);
        setAddAssignmentSettings(personAssignmentSettings);
        setPersonPane(personPane);

        searchBarTextField = new JTextField();
        searchBarTextField.setColumns(15);

        // Creates lens Image into a Button for searchBar
        ImageIcon lensImage = new ImageIcon(Paths.get(imgPath).toString());
        JLabel imgLabel = new JLabel(lensImage);
        JButton imgButton = new JButton();
        imgButton.setLayout(new BorderLayout());
        imgButton.add(imgLabel, BorderLayout.WEST);
        imgButton.setBackground(new Color(246, 245, 245, 255));
        imgButton.setBorder(null);
        imgButton.setBorderPainted(false);
        imgButton.setFocusable(false);
        imgButton.addActionListener(e -> {
            personList = Menu.fascade.getSearchedPerson(searchBarTextField.getText());
            updateButtons();
        });

        // Creates searchBar with imgButton and TextField for searching person with name
        searchBar = new JPanel();
        searchBar.setBorder(new TitledBorder(""));
        searchBar.setLayout(new GridLayout(2, 1));
        searchBar.setPreferredSize(new Dimension(0, 45));
        searchBar.add(imgButton);
        searchBar.add(searchBarTextField);

        // Creates list with buttons from Person
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setPreferredSize(new Dimension(140, 700));
        scrollPanePerson = new JScrollPane(contentPanel);

        addButtonsToContentPanel();

        this.setPreferredSize(new Dimension(170, 0));
        this.add(scrollPanePerson);
        this.add(searchBar, BorderLayout.SOUTH);
        this.setBorder(new TitledBorder("   Übersicht:  "));
        this.setVisible(true);

        if (!setVisibleSearchBar) {
            searchBar.setVisible(false);
        }

        // Automatically select the first person in the list and show his/hers info
        updatePanels(personList.get(0));
    }

    /**
     * Fills the contentPanel with JButtons
     * with the persons name on them
     */
    public void addButtonsToContentPanel() {
        personList = fascade.getAllPerson();
        for (int i = 0; i < personList.size(); i++) {
            JButton button = new JButton(fascade.getPersonsFullName(personList.get(i)));
            button.setMinimumSize(new Dimension(160, 25));
            button.setMaximumSize(new Dimension(160, 25));
            button.setBorder(null);
            button.setBorderPainted(false);
            button.setBackground(new Color(246, 245, 245, 255));
            button.setFocusable(false);

            button.addActionListener(e -> {
                updatePanels(fascade.getPersonByFullName(e.getActionCommand()));
                if (personPane != null)
                    personPane.setFocusedPerson(fascade.getPersonByFullName(e.getActionCommand()));
            });
            contentPanel.add(button);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Updates and refreshes the buttons inside the JScrollPane
     */
    public void updateButtons() {
        contentPanel.removeAll();
        addButtonsToContentPanel();
    }

    /**
     * When a Person in a JButton is selected,
     * the other JPanels get updates
     * @param person the selected Person
     */
    public void updatePanels(HRPerson person) {
        personInfo.update(person);
        if (personAssignmentSettings != null)
            personAssignmentSettings.updateComboBox(person);
        if (personPane != null)
            personPane.updateCheckBox(person);
    }

    /**
     * Sorts the JButtons in an order
     * @param type the type of the sorting (A-Z, Z-A, Keine)
     */
    public void sortPerson(@NotNull String type) {
        try {
            switch (type) {
                case "Keine":
                    personList = Menu.fascade.getAllPerson();
                    updateButtons();
                    break;
                case "A-Z":
                    personList = Menu.fascade.getAllPersonSortedAZ();
                    updateButtons();
                    break;
                case "Z-A":
                    personList = Menu.fascade.getAllPersonSortedZA();
                    updateButtons();
                    this.repaint();
                    break;
                default:
                    throw new UnknownSortingTypeException();
            }
        } catch (UnknownSortingTypeException e) {
            throw new RuntimeException(e);
        }
    }

    // SETTERS

    /**
     * Setter of PersonInfoPanel
     * @param personInfo object of PersonInfo
     */
    public void setPersonInfoPanel(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    /**
     * Setter of AddAssignmentSettings
     * @param personAssignmentSettings an object
     */
    public void setAddAssignmentSettings(PersonAssignmentSettings personAssignmentSettings) {
        this.personAssignmentSettings = personAssignmentSettings;
    }

    /**
     * Setter of personList
     * @param personList a List<HRPerson>
     */
    public void setPersonList(List<HRPerson> personList) {
        this.personList = personList;
    }

    /**
     * Setter of personPane
     * @param personPane a PersonPane object
     */
    public void setPersonPane(PersonPane personPane) {
        this.personPane = personPane;
    }
}

