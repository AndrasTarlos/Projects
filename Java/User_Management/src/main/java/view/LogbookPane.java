package view;

import fascades.FascadeLogbook;
import log.LogBook;
import utils.Menu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * <h1>Logbook</h1>
 * @author: Francesco Ryu
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * GUI for Logbook. Generates a panel for the specific tab in the Menu.java
 */

public class LogbookPane extends JPanel {

    DefaultListModel<String> defaultListModel;
    JScrollPane jScrollPane;
    JList jList;
    FascadeLogbook fascadeLogbook;


    public LogbookPane() {
        fascadeLogbook = Menu.fascadeLogbook;

        // Generates a simple list attached to a scrollbar to visualize the logbook

        addEntries();

        this.setLayout(new BorderLayout());
        this.add(jScrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     * Adds the entries to the logbook
     */
    private void addEntries() {
        defaultListModel = fascadeLogbook.getAllEntries();
        jList = new JList(defaultListModel);
        jScrollPane = new JScrollPane(jList);
    }

    /**
     * Updates the entries in the logbook
     */
    public void updateLogbook() {
        jList.removeAll();
        addEntries();
        jScrollPane.repaint();
        jScrollPane.revalidate();
    }
}