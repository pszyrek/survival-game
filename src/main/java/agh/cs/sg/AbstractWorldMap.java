package agh.cs.sg;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    protected Vector2d upperRight;
    protected Vector2d lowerLeft = new Vector2d(0, 0);

    public boolean canMoveTo(Vector2d position) {
        if(position.precedes(upperRight) && position.follows(lowerLeft)){
            return true;
        }
        return false;
    }

    public boolean place(Animal animal) {
        if(!isOccupied(animal.getPosition())) {
            animal.addObserver(this);
            animals.put(animal.getPosition(), animal);
            return true;
        }

        return false;
    }

    public boolean isOccupied(Vector2d position) {
        if(animals.containsKey(position)) {
            return true;
        }

        return false;
    }

    public Object objectAt(Vector2d position) {
        if(animals.containsKey(position)) {
            return animals.get(position);
        }

        return null;
    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        animals.put(newPosition, animals.remove(oldPosition));
    }

    @Override
    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(lowerLeft, upperRight);
    }
}
