package agh.cs.sg;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program is starting..");

        World world = new World();
        List<Vector2d> positions = Arrays.asList(new Vector2d(2,2), new Vector2d(8, 8),  new Vector2d(12, 8), new Vector2d(8, 4));
        IEngine engine = new SimulationEngine(world, positions);

        int i = 0;
        while(i < 1000) {
            engine.run();
            i++;
        }


        System.out.println(world.toString());

        int grassCount = 0;

        for(Field field : world.getMap().values()) {
            for(Animal animal : field.getAnimals()) {
                System.out.println(animal.getEnergy());
                System.out.println(animal.getPosition());
            }

            if(field.isGrassExists()) {
                grassCount  += 1;
            }
        }

        System.out.println(grassCount);

        System.out.println("Program is stopping..");
    }
}
