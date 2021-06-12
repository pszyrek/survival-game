package agh.cs.sg;

import java.util.*;

public class SimulationEngine implements IEngine {
    private final World world;
    private final int ANIMALS_SIZE = GameConfiguration.startNumberOfAnimals;

    SimulationEngine(World world, int width, int height) {
        this.world = world;

        this.init(width, height);
    }

    private void init(int width, int height) {
        int animalsElements = 0;
        while(ANIMALS_SIZE != animalsElements) {
            Random rand = new Random();

            int randX = rand.nextInt(width);
            int randY = rand.nextInt(height);
            Vector2d localization = new Vector2d(randX, randY);

            if (!world.isAnimalOccupied(localization)) {
                Animal animal = new Animal(localization, world);
                world.place(animal);
                animalsElements++;
            }
        }
    }

    @Override
    public void run() {
        world.resetNumberOfAnimals();
        world.resetValueOfAnimalsEnergy();
        world.resetNumberOfGrass();
        world.resetNumberOfChildren();
        world.resetLifeTimeForDeadAnimals();
        world.resetDominantGeneCounter();
        List<Animal> animals = new ArrayList<>();

        for(int i = 0; i < GameConfiguration.grassSizeRespawn; i++) {
            this.world.placeGrassOnRandomPosition();
        }

        for(Field field : world.getMap().values()) {
            if(field.isAnimalExists()){
                List<Animal> fieldAnimals = field.getAnimals();
                animals.addAll(fieldAnimals);
            }

            if(field.isGrassExists()) {
                world.increaseNumberOfGrass();
            }
        }

        for(Animal deadAnimal : world.getDeadAnimalsList()) {
            world.increaseLifeTimeForDeadAnimals(deadAnimal.getLifeTime());
        }

        for(Animal animal : animals) {
            world.increaseNumberOfAnimals();
            world.increaseValueOfAnimalsEnergy(animal.getEnergy());
            world.increaseNumberOfChildren(animal.getNumberOfChildren());

            world.updateDominantGenes(animal.getDominantGene());


            animal.move();
        }
    }
}
