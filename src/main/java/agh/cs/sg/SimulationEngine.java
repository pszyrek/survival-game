package agh.cs.sg;

import java.util.Iterator;
import java.util.List;

public class SimulationEngine implements IEngine {
    private List<MoveDirection> moveDirections;
    private List<Animal> animals;
    private IWorldMap map;


    SimulationEngine(List<MoveDirection> moveDirections, IWorldMap map, List<Vector2d> animalsVectorList) {
        animalsVectorList.forEach(animalVector -> {
            map.place(new Animal(map, animalVector));
        });

        this.moveDirections = moveDirections;
        this.animals = map.getAnimals();
        this.map = map;
    }

    public void run() {
        Iterator<Animal> animalIterator = animals.iterator();
        Iterator<MoveDirection> moveDirectionsIterator = moveDirections.iterator();

        while (moveDirectionsIterator.hasNext()) {
            System.out.println(map.toString());
            while (animalIterator.hasNext()) {
                animalIterator.next().move(moveDirectionsIterator.next());
            }
            animalIterator = animals.iterator();
        }
    }
}
