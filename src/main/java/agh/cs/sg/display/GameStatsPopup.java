package agh.cs.sg.display;

import agh.cs.sg.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameStatsPopup extends JFrame {
    JPanel panel = new JPanel();
    Game game;

    Container avgEnergy, numberOfAnimals, era, numberOfGrass, averageNumberOfChildren;

    GameStatsPopup(Game game, GameFrame gameFrame) {
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        this.game = game;

        this.avgEnergy = createContainer("Average energy", String.valueOf(game.world.getValueOfAnimalsEnergy() / game.world.getNumberOfAnimals()));
        this.numberOfAnimals = createContainer("Number of animals", String.valueOf(game.world.getNumberOfAnimals()));
        this.era = createContainer("Era", String.valueOf(game.world.getEra()));
        this.numberOfGrass = createContainer("Number of grass", String.valueOf(game.world.getNumberOfGrass()));
        this.averageNumberOfChildren = createContainer("Average number of children", String.valueOf(game.world.getNumberOfChildren() / game.world.getNumberOfAnimals()));

        panel.add(avgEnergy);
        panel.add(numberOfAnimals);
        panel.add(era);
        panel.add(numberOfGrass);
        panel.add(averageNumberOfChildren);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                gameFrame.removePopup();
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
        updateContainer(avgEnergy, String.valueOf(game.world.getValueOfAnimalsEnergy() / game.world.getNumberOfAnimals()));
        updateContainer(numberOfAnimals, String.valueOf(game.world.getNumberOfAnimals()));
        updateContainer(era, String.valueOf(game.world.getEra()));
        updateContainer(numberOfGrass, String.valueOf(game.world.getNumberOfGrass()));
        updateContainer(averageNumberOfChildren, String.valueOf(game.world.getNumberOfChildren() / game.world.getNumberOfAnimals()));
    }
}
