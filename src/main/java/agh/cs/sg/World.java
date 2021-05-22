package agh.cs.sg;

import agh.cs.sg.grass.Grass;
import agh.cs.sg.grass.GrassType;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class World implements IPositionChangeObserver {
    static final int GRASS_SIZE = 300;
    static int width;
    static int height;

    private final Map<Vector2d, Field> map = new ConcurrentHashMap<>();

    private final Vector2d upperRightMapCorner;
    private final Vector2d lowerLeftMapCorner = new Vector2d(0, 0);

    private final Vector2d lowerLeftJungleCorner = new Vector2d(10, 10);
    private final Vector2d upperRightJungleCorner = new Vector2d(30, 30);

    public World(int width, int height) {
        this.upperRightMapCorner = new Vector2d(width, height);
        this.width = width;
        this.height = height;

        int grassElements = 0;
        while(grassElements != GRASS_SIZE) {
            Random rand = new Random();
            int randX = rand.nextInt(width);
            int randY = rand.nextInt(height);

            Vector2d localization = new Vector2d(randX, randY);
            if(!isOccupied(localization)) {
                map.put(localization, new Field(new Grass(isInJungleRange(localization) ? GrassType.JUNGLE  : GrassType.STEPPE)));
                grassElements++;
            }
        }
    }

public Vector2d positionChange(Animal animal, Vector2d position) {
    Vector2d demandPosition = position.add(animal.getOrientation().toUnitVector());
        if(isInMapRange(demandPosition)) {
            Object objectOnCurrentPosition = objectAt(position);
            if(objectOnCurrentPosition instanceof Field) {
                Field field = (Field) objectOnCurrentPosition;

                if((field.getAnimals().size() > 1)) {
                    field.removeElement(animal);
                } else {
                    map.remove(position);
                }
            }

            if(map.containsKey(demandPosition)) {
                Object objectOnDemandPosition = objectAt(demandPosition);
                if (objectOnDemandPosition instanceof Field) {
                    Field field = (Field) objectOnDemandPosition;

                    if(field.isAnimalExists()) {
                        field.addElement(animal);
                        List<Animal> strongestParents = field.getStrongestParents();

                        if(field.isGrassExists()) {
                            strongestParents.get(1).eatGrass(field.getGrass().getEnergy());
                            field.removeElement(field.getGrass());
                        }

                        int minEnergyValueForReproduce = 4;

                        if(strongestParents.get(0).getEnergy() > minEnergyValueForReproduce && strongestParents.get(1).getEnergy() > minEnergyValueForReproduce) {
                            Animal childAnimal = strongestParents.get(0).reproduce(strongestParents.get(1), this, demandPosition);
                            field.addElement(childAnimal);
                        }
                    } else {
                        if(field.isGrassExists()) {
                            animal.eatGrass(field.getGrass().getEnergy());
                            field.removeElement(field.getGrass());
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

    public void removePosition(Animal animal, Vector2d position) {
        Object objectOnCurrentPosition = objectAt(position);
        if(objectOnCurrentPosition instanceof Field) {
            Field field = (Field) objectOnCurrentPosition;

            field.removeElement(animal);
        }
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
        return isAnimalOccupied(position) || isGrassOccupied(position);
    }

    public Object objectAt(Vector2d position) {
        if(map.get(position) != null) {
            return map.get(position);
        }
        return null;
    }

    public void place(Animal animal) {
        if(!isAnimalOccupied(animal.getPosition())) {
            if(isGrassOccupied(animal.getPosition())) {
                Object objectOnDemandPosition = objectAt(animal.getPosition());
                if (objectOnDemandPosition instanceof Field) {
                    Field field = (Field) objectOnDemandPosition;

                    animal.eatGrass(field.getGrass().getEnergy());
                }
            }

            map.put(animal.getPosition(), new Field(animal));
        }
    }

    public void placeGrass() {
        Random rand = new Random();
        Vector2d localization = new Vector2d(rand.nextInt(this.width), rand.nextInt(this.height));

        Grass grass = new Grass(isInJungleRange(localization) ? GrassType.JUNGLE  : GrassType.STEPPE);

        if(!isOccupied(localization)) {
            map.put(localization, new Field(grass));
        } else {
            Object objectOnDemandPosition = objectAt(localization);
            if (objectOnDemandPosition instanceof Field) {
                Field field = (Field) objectOnDemandPosition;

                if(!field.isGrassExists()) {
                    field.addElement(grass);
                }
            }
        }
    }

    public void moveAnimal(Vector2d demandPosition, Animal animal) {
        map.put(demandPosition, new Field(animal));
    }

    public boolean isInMapRange(Vector2d position) {
        return position.precedes(upperRightMapCorner) && position.follows(lowerLeftMapCorner);
    }

    public boolean isInJungleRange(Vector2d position) {
        return position.precedes(upperRightJungleCorner) && position.follows(lowerLeftJungleCorner);
    }

    @Override
    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(lowerLeftMapCorner, upperRightMapCorner);
    }
}
