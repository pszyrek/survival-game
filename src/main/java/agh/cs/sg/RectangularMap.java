package agh.cs.sg;


import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    private List<Animal> animals = new ArrayList<>();
    private Vector2d upperRight;
    private Vector2d lowerLeft = new Vector2d(0, 0);

    RectangularMap(int width, int height) {
        this.upperRight = new Vector2d(width, height);
    }

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

    @Override
    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(lowerLeft, upperRight);
    }
}
