package agh.cs.sg;

import java.util.*;

public class SimulationEngine implements IEngine {
    private final Map<Vector2d, Field> map;
    private World world;

    SimulationEngine(World map, List<Vector2d> animalsVectorList) {
        animalsVectorList.forEach(animalVector -> {
            Animal animal = new Animal(animalVector, map);
            map.place(animal);
        });

        this.map = map.getMap();
        this.world = map;
    }

    public void run() {
        List<Animal> animals = new ArrayList<>();

        this.world.placeGrass();

        for(Field field : map.values()) {
            if(field.isAnimalExists()){
                List<Animal> fieldAnimals = field.getAnimals();
                animals.addAll(fieldAnimals);
            }
        }

        for(Animal animal : animals) {
            animal.move();
        }
    }
}
