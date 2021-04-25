package agh.cs.sg;

import java.util.Arrays;
import java.util.List;

public class World {
    public static void main(String[] args) {
        System.out.println("Program is starting..");

        List<MoveDirection> directions = new OptionsParser().parse(args);
        IWorldMap grassField = new GrassField(5);
        List<Vector2d> positions = Arrays.asList(new Vector2d(2,2), new Vector2d(2,3));
        IEngine engine = new SimulationEngine(directions, grassField, positions);
        engine.run();

        System.out.println(grassField.toString());

        System.out.println("Program is stopping..");
    }
}
