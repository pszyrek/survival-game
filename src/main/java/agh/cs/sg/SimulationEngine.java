package agh.cs.sg;

import java.util.*;

public class SimulationEngine implements IEngine {
    private List<MoveDirection> moveDirections;
    private Map<Vector2d, Animal> animals;


    SimulationEngine(List<MoveDirection> moveDirections, IWorldMap map, List<Vector2d> animalsVectorList) {
        animalsVectorList.forEach(animalVector -> {
            Animal animal = new Animal(map, animalVector);
            map.place(animal);
        });

        this.moveDirections = moveDirections;
        this.animals = map.getAnimals();
    }

    public void run() {
        Iterator<MoveDirection> moveDirectionsIterator = moveDirections.iterator();

        while (moveDirectionsIterator.hasNext()) {
            for(Animal animal : animals.values()) {
                animal.move(moveDirectionsIterator.next());
            }
        }
    }
}
