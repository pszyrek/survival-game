package agh.cs.sg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GrassField extends AbstractWorldMap {
    private List<Grass> grassList = new ArrayList<>();

    GrassField(int grassCount) {
        this.upperRight = new Vector2d(20, 20);

        int maxX = 10;
        int maxY = 10;

        grassList.add(new Grass(new Vector2d(2, 3)));

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
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals) {
            if(animal.getPosition().equals(position)) {
                return true;
            }
        }

        for (Grass grass : grassList) {
            if(grass.getPosition().equals(position)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if(animal.getPosition().equals(position)) {
                return animal;
            }
        }

        for (Grass grass : grassList) {
            if(grass.getPosition().equals(position)) {
                return grass;
            }
        }


        return null;
    }

    public void eatGrass(Vector2d position) {
        Iterator<Grass> grassIterator = grassList.iterator();

        while(grassIterator.hasNext()) {
            if(grassIterator.next().getPosition().equals(position)) {
                grassIterator.remove();
            }
        }
    }

    @Override
    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(lowerLeft, upperRight);
    }
}
