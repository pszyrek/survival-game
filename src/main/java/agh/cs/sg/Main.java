package agh.cs.sg;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program is starting..");

        List<Integer> directions = new OptionsParser().parse(args);
        World world = new World();
        List<Vector2d> positions = Arrays.asList(new Vector2d(2,2), new Vector2d(2,4));
        IEngine engine = new SimulationEngine(world, positions);
        engine.run(directions);

        System.out.println(world.toString());

        System.out.println("Program is stopping..");
    }
}
