package agh.cs.sg;

import java.util.Map;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);

    boolean place(Animal animal);

    boolean isGrassOccupied(Vector2d position);

    boolean isOccupied(Vector2d position);

    void eatGrass(Vector2d position);

    Object objectAt(Vector2d position);

    Map<Vector2d, Animal> getAnimals();
}