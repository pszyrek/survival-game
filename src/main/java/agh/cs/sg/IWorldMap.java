package agh.cs.sg;

import java.util.ArrayList;
import java.util.List;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);

    boolean place(Animal animal);

    boolean isOccupied(Vector2d position);

    Object objectAt(Vector2d position);

    List<Animal> getAnimals();
}