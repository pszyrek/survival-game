package agh.cs.sg;

import java.util.*;

public class SimulationEngine implements IEngine {
    private final Map<Vector2d, Field> map;
    private World world;

    SimulationEngine(World map, int width, int height, int numberOfAnimals) {
        Random rand = new Random();
        for(int i = 0; i < numberOfAnimals; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            Animal animal = new Animal(new Vector2d(x, y), map);
            map.place(animal);
        }

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
