package agh.cs.sg;

import java.util.*;

public class SimulationEngine implements IEngine {
    private Map<Vector2d, Animal> animals;

    SimulationEngine(IWorldMap map, List<Vector2d> animalsVectorList) {
        animalsVectorList.forEach(animalVector -> {
            Animal animal = new Animal(map, animalVector);
            map.place(animal);
        });

        this.animals = map.getAnimals();
    }

    public void run(List<Integer> moveDirections) {
        for(Integer moveDirection : moveDirections) {
            for(Animal animal : animals.values()) {
                animal.move(moveDirection);
            }
        }
    }
}
