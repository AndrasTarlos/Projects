package view;

import fascades.Fascade;
import view.components.DataInfoPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import utils.Menu;

/**
 * <h1>DataPane</h1>
 * @author: Francesco Ryu/Andras Tarlos
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * GUI for "Stammdaten". Generates a panel for the specific tab in the Menu.java
 */

public class DataPane extends JPanel {
    DataInfoPanel dataInfoPanel;
    JPanel componentsPanel;
    JPanel companyPanel;
    JLabel companylabel;
    JTextField companyTextField;
    Fascade fascade;

    public DataPane() {
        fascade = utils.Menu.fascade;
        companyPanel = new JPanel();
        companyPanel.setLayout(new GridLayout(1, 2));

        companylabel = new JLabel("Firma:");
        companyTextField = new JTextField(fascade.getCompanyName());
        companyTextField.setColumns(15);
        companyTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fascade.setCompanyName(companyTextField.getText());
                }
            }
        });

        companyPanel.add(companylabel);
        companyPanel.add(companyTextField);

        componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));


        // Generates dataInfoPanel 3 times with the matching labelName
        String[] labelNames = {"Abteilung:", "Funktionen:", "Teams:"};

        for (int i = 0; i < 3; i++) {
            dataInfoPanel = new DataInfoPanel(labelNames[i]);
            componentsPanel.add(dataInfoPanel);
        }

        this.setLayout(new BorderLayout());
        this.add(companyPanel, BorderLayout.NORTH);
        this.add(componentsPanel, BorderLayout.CENTER);
    }
}
