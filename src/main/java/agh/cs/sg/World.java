package agh.cs.sg;

import agh.cs.sg.grass.Grass;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class World implements IPositionChangeObserver {
    static final int GRASS_SIZE = 10;
    static final int MAX_Y = 20;
    static final int MAX_X = 20;

    private final Map<Vector2d, Field> map = new ConcurrentHashMap<>();

    private final Random rand = new Random();
    private final Vector2d upperRight = new Vector2d(20, 20);
    private Vector2d lowerLeft = new Vector2d(0, 0);

    public World() {
        map.put(new Vector2d(2, 3), new Field(new Grass()));
//        map.put(new Vector2d(2, 4), new Field(new Grass()));
//        int grassElements = 0;
//        while(grassElements != GRASS_SIZE) {
//            int randX = rand.nextInt(MAX_X);
//            int randY = rand.nextInt(MAX_Y);
//
//            Vector2d localization = new Vector2d(randX, randY);
//            if(!isOccupied(localization)) {
//                map.put(localization, new Field(new Grass()));
//                grassElements++;
//            }
//        }
    }

public Vector2d positionChange(Animal animal, Vector2d position) {
    Vector2d demandPosition = position.add(animal.getOrientation().toUnitVector());
        if(isInMapRange(demandPosition)) {
            map.remove(position);

            if(map.containsKey(demandPosition)) {
                Object object = objectAt(demandPosition);
                if (object != null && object instanceof Field) {
                    Field field = (Field) object;

                    if(field.isAnimalExists()) {
                        field.addElement(animal);
                    } else {
                        if(field.isGrassExists()) {
                            animal.eatGrass();
                            field.removeElement(field.getElements().get(0));
                        }
                        field.addElement(animal);
                    }
                }
            } else {
                moveAnimal(demandPosition,  animal);
            }

            return demandPosition;
        }

        return position;
    }

    public Map<Vector2d, Field> getMap() {
        return this.map;
    }

    public boolean isGrassOccupied(Vector2d position) {
        return map.containsKey(position) && map.get(position).isGrassExists();
    }

    public boolean isAnimalOccupied(Vector2d position) {
        return map.containsKey(position) && map.get(position).isAnimalExists();
    }

    public boolean isOccupied(Vector2d position) {
        if(isAnimalOccupied(position) || isGrassOccupied(position)) {
            return true;
        }

        return false;
    }

    public Object objectAt(Vector2d position) {
        if(map.get(position) != null) {
            return map.get(position);
        }
        return null;
    }

    public void place(Animal animal) {
        if(!isOccupied(animal.getPosition())) {
            map.put(animal.getPosition(), new Field(animal));
        }
    }

    public void moveAnimal(Vector2d demandPosition, Animal animal) {
        map.put(demandPosition, new Field(animal));
    }

    public boolean isInMapRange(Vector2d position) {
        if(position.precedes(upperRight) && position.follows(lowerLeft)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(lowerLeft, upperRight);
    }
}
