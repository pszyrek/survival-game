package agh.cs.sg;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private Map<Vector2d, Grass> grassList = new HashMap<>();

    GrassField(int grassCount) {
        this.upperRight = new Vector2d(20, 20);

        int maxX = 10;
        int maxY = 10;

        grassList.put(new Vector2d(2, 3),  new Grass(new Vector2d(2, 3)));

        Random rand = new Random();

        while(grassList.size() != grassCount) {
            int randX = rand.nextInt(maxX);
            int randY = rand.nextInt(maxY);

            Vector2d vector = new Vector2d(randX, randY);

            if(!isOccupied(vector)) {
                Grass grass = new Grass(vector);
                grassList.put(vector, grass);
            }
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if(animals.containsKey(position)) {
            return true;
        }

        if(grassList.containsKey(position)) {
            return true;
        }

        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {

        if(animals.containsKey(position)) {
            return animals.get(position);
        }

        if(grassList.containsKey(position)) {
            return grassList.get(position);
        }

        return null;
    }

    public void eatGrass(Vector2d position) {
        animals.get(position).addEnergy(2);
        grassList.remove(position);
    }
}
