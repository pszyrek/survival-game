package agh.cs.sg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World {
    public static void main(String[] args) {
        System.out.println("Program is starting..");
        List<MoveDirection> directions = new OptionsParser().parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        List<Vector2d> positions = Arrays.asList(new Vector2d(2,2), new Vector2d(3,4));
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        System.out.println("Program is stopping..");
    }
}
