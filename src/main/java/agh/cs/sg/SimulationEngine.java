package agh.cs.sg;

import java.util.*;

public class SimulationEngine implements IEngine {
    private Map<Vector2d, Field> map;

    SimulationEngine(World map, List<Vector2d> animalsVectorList) {
        animalsVectorList.forEach(animalVector -> {
            Animal animal = new Animal(map, animalVector);
            map.place(animal);
        });

        this.map = map.getMap();
    }

    public void run(List<Integer> moveDirections) {
        for(Integer moveDirection : moveDirections) {
            for(Field field : map.values()) {
                Optional<MapElement> object = field.getAnimal();
                if (object.isPresent()) {
                    Animal animal = (Animal) object.get();
                    animal.move(moveDirection);
                }
            }
        }
    }
}
