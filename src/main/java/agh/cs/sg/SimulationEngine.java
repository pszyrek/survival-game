package agh.cs.sg;

import java.util.*;

public class SimulationEngine implements IEngine {
    private Map<Vector2d, Field> map;

    SimulationEngine(World map, List<Vector2d> animalsVectorList) {
        animalsVectorList.forEach(animalVector -> {
            Animal animal = new Animal(animalVector, map);
            map.place(animal);
        });

        this.map = map.getMap();
    }

    public void run(List<Integer> moveDirections) {
        for(Integer moveDirection : moveDirections) {
            for(Field field : map.values()) {
                if(field.isAnimalExists()){
                    List<Animal> animals = field.getAnimals();
                    for(Animal animal : animals) {
                        animal.move(moveDirection);
                    }
                }

            }
        }
    }
}
