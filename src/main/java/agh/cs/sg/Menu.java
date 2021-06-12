package agh.cs.sg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Menu implements ActionListener {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton button = new JButton("Run");
    Container widthContainer = createContainer(GameConfiguration.width,  "Width of map");
    Container heightContainer = createContainer(GameConfiguration.height,  "Height of map");
    Container tileSizeContainer = createContainer(GameConfiguration.tileSize,  "Size of one tile on map");
    Container startNumberOfAnimalsContainer = createContainer(GameConfiguration.startNumberOfAnimals,  "Start number of animals on map");
    Container valueOfDecreasingEnergyContainer = createContainer(GameConfiguration.valueOfDecreasingEnergy,  "Value of decreasing energy of animal");
    Container minEnergyToReproduceContainer = createContainer(GameConfiguration.initialValueOfAnimalEnergy / 2,  "Minimal energy to reproduce");
    Container numberOfWorlds = createContainer(GameConfiguration.numberOfWorlds,  "Number of worlds");
    Container grassSize = createContainer(GameConfiguration.grassSize,  "Number of grass");
    Container grassSizeRespawn = createContainer(GameConfiguration.grassSizeRespawn,  "Number of grass to respawn");
    Container delay = createContainer(GameConfiguration.delay,  "Delay (in milliseconds)");
    Container initialValueOfAnimalEnergy = createContainer(GameConfiguration.initialValueOfAnimalEnergy,  "InitialValue of Animal energy");
    Container jungleEnergyValue = createContainer(GameConfiguration.jungleEnergyValue,  "Jungle grass energy value");
    Container steppeEnergyValue = createContainer(GameConfiguration.steppeEnergyValue,  "Steppe grass energy value");
    Container jungleRatio = createContainer(GameConfiguration.jungleRatio,  "Jungle ratio to map");

    private Container createContainer(int defaultValue, String labelValue) {
        Container container = new Container(); // Object który może zawierać inne elementy AWT
        container.setLayout(new GridLayout(3, 1));
        JLabel label = new JLabel(labelValue);

        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        JTextField jTextField = new JTextField(String.valueOf(defaultValue), 30);
        jTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    jTextField.setEditable(true);
                    errorLabel.setText("");
                } else {
                    jTextField.setEditable(false);
                    errorLabel.setText("* Enter only numeric digits(0-9)");

                }
            }
        });

        container.add(label);
        container.add(jTextField);
        container.add(errorLabel);

        container.setVisible(true);

        return container;
    }

    private int getFieldValue(Container container) {
        JTextField jTextField = (JTextField) container.getComponent(1);
        return Integer.valueOf(jTextField.getText());
    }

    public Menu() {
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        panel.add(heightContainer);
        panel.add(widthContainer);
        panel.add(tileSizeContainer);
        panel.add(startNumberOfAnimalsContainer);
        panel.add(valueOfDecreasingEnergyContainer);
        panel.add(minEnergyToReproduceContainer);
        panel.add(numberOfWorlds);
        panel.add(grassSize);
        panel.add(grassSizeRespawn);
        panel.add(delay);
        panel.add(initialValueOfAnimalEnergy);
        panel.add(jungleEnergyValue);
        panel.add(steppeEnergyValue);
        panel.add(jungleRatio);

        button.addActionListener(this);
        panel.add(button);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game configuration menu");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            GameConfiguration.height = getFieldValue(heightContainer);
            GameConfiguration.width = getFieldValue(widthContainer);
            GameConfiguration.tileSize = getFieldValue(tileSizeContainer);
            GameConfiguration.startNumberOfAnimals = getFieldValue(startNumberOfAnimalsContainer);
            GameConfiguration.valueOfDecreasingEnergy = getFieldValue(valueOfDecreasingEnergyContainer);
            GameConfiguration.minEnergyToReproduce = getFieldValue(minEnergyToReproduceContainer);
            GameConfiguration.numberOfWorlds = getFieldValue(numberOfWorlds);
            GameConfiguration.grassSize = getFieldValue(grassSize);
            GameConfiguration.grassSizeRespawn = getFieldValue(grassSizeRespawn);
            GameConfiguration.delay = getFieldValue(delay);
            GameConfiguration.initialValueOfAnimalEnergy = getFieldValue(initialValueOfAnimalEnergy);
            GameConfiguration.jungleEnergyValue = getFieldValue(jungleEnergyValue);
            GameConfiguration.steppeEnergyValue = getFieldValue(steppeEnergyValue);
            GameConfiguration.jungleRatio = getFieldValue(jungleRatio);

            for(int i = 0; i < GameConfiguration.numberOfWorlds; i++) {
                Game game = new Game();
                game.start(true);
            }
        }
    }
}
