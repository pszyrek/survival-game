package agh.cs.sg;

import java.util.Map;
import java.util.Optional;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);

    boolean place(Animal animal);

    boolean isGrassOccupied(Vector2d position);

    boolean isOccupied(Vector2d position);

    void eatGrass(Vector2d position);

    Optional<Object> objectAt(Vector2d position);

    Map<Vector2d, Animal> getAnimals();
}