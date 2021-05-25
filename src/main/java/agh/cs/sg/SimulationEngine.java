package agh.cs.sg;

import java.util.*;

public class SimulationEngine implements IEngine {
    private final World world;

    SimulationEngine(World world, int width, int height, int numberOfAnimals, int valueOfDecreasingEnergy) {
        this.world = world;

        this.init(width, height, numberOfAnimals, valueOfDecreasingEnergy);
    }

    private void init(int width, int height, int numberOfAnimals, int valueOfDecreasingEnergy) {
        Random rand = new Random();
        for(int i = 0; i < numberOfAnimals; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            Animal animal = new Animal(new Vector2d(x, y), world, valueOfDecreasingEnergy);
            world.place(animal);
        }
    }

    @Override
    public void run() {
        List<Animal> animals = new ArrayList<>();

        this.world.placeGrassOnRandomPosition();

        for(Field field : world.getMap().values()) {
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
