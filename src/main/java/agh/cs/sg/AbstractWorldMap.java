package agh.cs.sg;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractWorldMap implements IWorldMap {
    protected List<Animal> animals = new ArrayList<>();

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
            animals.add(animal);
            return true;
        }

        return false;
    }

    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals) {
            if(animal.getPosition().equals(position)) {
                return true;
            }
        }

        return false;
    }

    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if(animal.getPosition().equals(position)) {
                return animal;
            }
        }

        return null;
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
