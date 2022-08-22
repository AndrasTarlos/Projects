package view.popups;

import javax.swing.*;
import java.awt.*;

import org.jdesktop.swingx.prompt.PromptSupport;
import utils.Menu;
import view.components.DataInfoPanel;

/**
 * <h1>CreateFunction</h1>
 * @author: Julian Janik, Andras Tarlos
 * @version: 1.0
 * @date: 10.06.2022
 * <h2>Description</h2>
 * This little popup GUI is responsible for the creation of new data.
 */

public class CreateFunction extends JDialog {
    JLabel label2 = new JLabel();

    public CreateFunction(String title, DataInfoPanel dataInfoPanel) {
        getContentPane().setLayout(new BorderLayout());
        JPanel panel1 = new JPanel();
        getContentPane().add(panel1, BorderLayout.NORTH);
        JPanel panel2 = new JPanel();
        getContentPane().add(panel2, BorderLayout.SOUTH);

        panel1.setLayout(new GridLayout(1,2));
        panel1.add(new JLabel(title));

        JTextField txt = new JTextField();
        PromptSupport.setPrompt("Text eingeben", txt);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, txt);
        panel1.add(txt);

        JButton abbrechen = new JButton("Abbrechen");
        abbrechen.setFocusable(false);
        abbrechen.addActionListener(e -> {this.setVisible(false);});

        JButton speichern = new JButton("Speichern");
        speichern.setFocusable(false);
        speichern.addActionListener(e -> {
            if (!txt.getText().equals("")) {
                switch (title) {
                    case "Abteilung" -> Menu.fascade.createDepartment(txt.getText());
                    case "Funktion" -> Menu.fascade.createJobFunction(txt.getText());
                    case "Team" -> Menu.fascade.createTeam(txt.getText());
                }
                this.setVisible(false);
                dataInfoPanel.updateButtons();
            }
        });

        panel2.setLayout(new GridLayout(1,3));
        panel2.add(label2);
        panel2.add(abbrechen);
        panel2.add(speichern);

        setTitle(title + " erfassen");
        setVisible(true);
        setResizable(false);
        setSize(350, 150);
        this.setLocation(250, 250);
    }
}
