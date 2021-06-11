package agh.cs.sg.display;

import agh.cs.sg.Animal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AnimalStatsPopup extends JFrame {
    JPanel panel = new JPanel();
    Animal animal;

    Container energy;
    Container position;
    Container genes;
    Container numberOfChildren;
    Container numberOfDescendants;

    AnimalStatsPopup(Animal animal, GamePanel gamePanel) {
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        this.animal = animal;

        this.energy = createContainer("Energy", String.valueOf(animal.getEnergy()));
        this.position = createContainer("Position", animal.getPosition().toString());
        this.genes = createContainer("Genes", animal.getGenes().toString());
        this.numberOfChildren  = createContainer("Number of children", String.valueOf(animal.getNumberOfChildren()));
        this.numberOfDescendants = createContainer("Number of descendants", String.valueOf(animal.getNumberOfDescendants()));

        panel.add(energy);
        panel.add(position);
        panel.add(genes);
        panel.add(numberOfChildren);
        panel.add(numberOfDescendants);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                gamePanel.removePopup();
                gamePanel.repaint();
            }
        });

        this.add(panel, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    private Container createContainer(String description, String value) {
        Container container = new Container(); // Object który może zawierać inne elementy AWT
        container.setLayout(new GridLayout(2, 2));
        JLabel descriptionLabel = new JLabel(description + ": ");
        JLabel valueLabel = new JLabel(value);

        container.add(descriptionLabel);
        container.add(valueLabel);

        container.setVisible(true);

        return container;
    }

    private void updateContainer(Container container, String value) {
        JLabel label = (JLabel) container.getComponent(1);
        label.setText(value);
    }

    public void update() {
        updateContainer(energy, String.valueOf(animal.getEnergy()));
        updateContainer(position, animal.getPosition().toString());
        updateContainer(genes, animal.getGenes().toString());
        updateContainer(numberOfChildren, String.valueOf(animal.getNumberOfChildren()));
        updateContainer(numberOfDescendants, String.valueOf(animal.getNumberOfDescendants()));
    }
}
