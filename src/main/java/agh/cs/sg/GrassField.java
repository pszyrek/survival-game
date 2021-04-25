package agh.cs.sg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassField implements IWorldMap {
    private List<Grass> grassList = new ArrayList<>();
    private Vector2d upperRight;
    private Vector2d lowerLeft = new Vector2d(0, 0);

    GrassField(int grassCount) {
        int maxX = (int) Math.sqrt(grassCount*10);
        int maxY = (int) Math.sqrt(grassCount*10);

        this.upperRight = new Vector2d(maxX, maxY);

        Random rand = new Random();

        while(grassList.size() != grassCount) {
            int randX = rand.nextInt(maxX);
            int randY = rand.nextInt(maxY);

            Vector2d vector = new Vector2d(randX, randY);

            if(!isOccupied(vector)) {
                grassList.add(new Grass(vector));
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public boolean place(Animal animal) {
        return false;
    }

    public boolean isOccupied(Vector2d position) {
        for (Grass grass : grassList) {
            if(grass.getPosition().equals(position)) {
                return true;
            }
        }

        return false;
    }

    public Object objectAt(Vector2d position) {
        for (Grass grass : grassList) {
            if(grass.getPosition().equals(position)) {
                return grass;
            }
        }

        return null;
    }

    @Override
    public List<Animal> getAnimals() {
        return null;
    }

    @Override
    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(lowerLeft, upperRight);
    }
}
