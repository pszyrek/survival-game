package agh.cs.sg.display;

import agh.cs.sg.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel implements MouseListener {
    private int width;
    private int height;
    private int tileSize;
    private World world;
    private GameFrame frame;
    private AnimalStatsPopup animalStatsPopup = null;
    private Animal animal;

    private int xTile = -1;
    private int yTile = -1;

    GamePanel(int width, int height, int tileSize, World world, GameFrame frame) {
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);

        addMouseListener(this);

        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.world = world;
        this.frame = frame;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int x = 0; x < this.width; x++) {
            for(int y = 0; y < this.height; y++) {
                Vector2d position = new Vector2d(x, y);
                if(!world.isOccupied(position)) {
                    if(world.isInJungleRange(position)) {
                        g.setColor(new Color(81,149,72));
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    } else  {
                        g.setColor(new Color(160,197,95));
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                } else if(world.isAnimalOccupied(position)) {
                    Field field = world.findField(position);
                    Animal strongestAnimal = field.strongestAnimal();

                    if(strongestAnimal.isMarked()) {
                        g.setColor(new Color(0, 0,0));
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    } else {
                        g.setColor(strongestAnimal.getColor().colorNameToRgb());
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }


                    if (strongestAnimal.isDominantMarked()) {
                        g.setColor(new Color(0, 35, 196));
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    } else {
                        g.setColor(strongestAnimal.getColor().colorNameToRgb());
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                } else {
                    if(world.isGrassOccupied(position)) {
                        g.setColor(new Color(190,242,2));
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                }

                if(frame.isPaused) {
                    if (this.xTile == x && this.yTile == y) {
                        g.setColor(new Color(0, 0, 0));
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                }
            }
        }
    }

    public void updatePopup() {
        if(this.animalStatsPopup != null) {
            this.animalStatsPopup.update();
        }
    }

    public void removePopup() {
        this.animalStatsPopup.dispose();
        this.animalStatsPopup = null;
        this.xTile = -1;
        this.yTile = -1;
        animal.unmark();
    }

    public void markAnimalsWithDominantGene(Animal animal) {
        for(Field field : world.getMap().values()) {
            if(field.isAnimalExists() && field.strongestAnimal().getDominantGene() == animal.getDominantGene()) {
                field.strongestAnimal().markDominant();
            }
        }

        repaint();
    }

    public void unmarkAnimalsWithDominantGene(Animal animal) {
        for(Field field : world.getMap().values()) {
            if(field.isAnimalExists() && field.strongestAnimal().getDominantGene() == animal.getDominantGene()) {
                field.strongestAnimal().unmarkDominant();
            }
        }

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(frame.isPaused) {
            if(this.animalStatsPopup != null) {
                this.removePopup();
            }

            this.xTile = (e.getX() - 1) / tileSize;
            this.yTile = (e.getY() - 1) / tileSize;

            repaint();

            if(world.getMap().containsKey(new Vector2d(this.xTile, this.yTile))) {
                Field field = world.findField(new Vector2d(this.xTile, this.yTile));

                if(field.isAnimalExists()) {
                    this.animal = field.strongestAnimal();
                    animal.mark();
                    this.animalStatsPopup = new AnimalStatsPopup(animal, this);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
