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

    public void run() {
        List<Animal> animals = new ArrayList<>();

        for(Field field : map.values()) {
            if(field.isAnimalExists()){
                List<Animal> fieldAnimals = field.getAnimals();
                for(Animal animal : fieldAnimals) {
                    animals.add(animal);
                }
            }
        }

        for(Animal animal : animals) {
            animal.move();
        }
    }
}
