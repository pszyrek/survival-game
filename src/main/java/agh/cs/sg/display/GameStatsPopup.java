package agh.cs.sg.display;

import agh.cs.sg.Game;
import agh.cs.sg.WriteStatsToJSON;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GameStatsPopup extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    Game game;

    Container avgEnergy, numberOfAnimals, era, numberOfGrass, averageNumberOfChildren, averageLifeTimeForDeadAnimals;
    ArrayList<Container> dominantGenesNumberList = new ArrayList<>();
    JButton button = new JButton("Save settings");

    GameStatsPopup(Game game, GameFrame gameFrame) {
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        this.game = game;

        this.avgEnergy = createContainer("Average energy", String.valueOf(game.world.getValueOfAnimalsEnergy() / game.world.getNumberOfAnimals()));
        this.numberOfAnimals = createContainer("Number of animals", String.valueOf(game.world.getNumberOfAnimals()));
        this.era = createContainer("Era", String.valueOf(game.world.getEra()));
        this.numberOfGrass = createContainer("Number of grass", String.valueOf(game.world.getNumberOfGrass()));
        this.averageNumberOfChildren = createContainer("Average number of children", String.valueOf(game.world.getNumberOfChildren() / game.world.getNumberOfAnimals()));
        this.averageLifeTimeForDeadAnimals = createContainer("Average life time for dead animals", String.valueOf(game.world.getLifeTimeForDeadAnimals() / game.world.getDeadAnimalsList().size()));

        panel.add(avgEnergy);
        panel.add(numberOfAnimals);
        panel.add(era);
        panel.add(numberOfGrass);
        panel.add(averageNumberOfChildren);
        panel.add(averageLifeTimeForDeadAnimals);

        generateDominantGenesList();

        button.addActionListener(this);
        panel.add(button);

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
        updateContainer(averageLifeTimeForDeadAnimals, String.valueOf(game.world.getLifeTimeForDeadAnimals() / game.world.getDeadAnimalsList().size()));

        int index = 0;
        for(Container container : dominantGenesNumberList) {
            updateContainer(container, String.valueOf(game.world.getDominantGeneNumber(index)));
            index++;
        }
    }

    public void generateDominantGenesList() {
        for(int i = 0; i < 8; i++) {
            Container container = createContainer("Dominant gene - " + i, String.valueOf(game.world.getDominantGeneNumber(i)));
            dominantGenesNumberList.add(container);
            panel.add(container);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            WriteStatsToJSON.saveFile(String.valueOf(game.world.getValueOfAnimalsEnergy() / game.world.getNumberOfAnimals()),
                    String.valueOf(game.world.getNumberOfAnimals()), String.valueOf(game.world.getEra()),
                    String.valueOf(game.world.getNumberOfGrass()),
                    String.valueOf(game.world.getNumberOfChildren() / game.world.getNumberOfAnimals()),
                    String.valueOf(game.world.getLifeTimeForDeadAnimals() / game.world.getDeadAnimalsList().size()));
            System.out.println("saved");
        }
    }
}
