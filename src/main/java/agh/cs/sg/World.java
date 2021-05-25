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

    private final int minEnergyToReproduce;

    public World(int width, int height, int minEnergyToReproduce) {
        this.upperRightMapCorner = new Vector2d(width, height);
        this.width = width;
        this.height = height;

        this.minEnergyToReproduce = minEnergyToReproduce;

        int grassElements = 0;
        while (grassElements != GRASS_SIZE) {
            Random rand = new Random();
            int randX = rand.nextInt(width);
            int randY = rand.nextInt(height);

            Vector2d localization = new Vector2d(randX, randY);
            if (!isOccupied(localization)) {
                map.put(localization, new Field(new Grass(isInJungleRange(localization) ? GrassType.JUNGLE : GrassType.STEPPE)));
                grassElements++;
            }
        }
    }

    public Vector2d positionChange(Animal animal, Vector2d position) {
        Vector2d demandPosition = position.add(animal.getOrientation().toUnitVector());
        if (isInMapRange(demandPosition)) {
            Field field = findField(position);
            field.removeElement(animal);

            if (map.containsKey(demandPosition)) {
                Field fieldOnDemandPosition = findField(demandPosition);

                if (fieldOnDemandPosition.isAnimalExists()) {
                    fieldOnDemandPosition.addElement(animal);
                    List<Animal> strongestParents = fieldOnDemandPosition.getStrongestParents();

                    if (fieldOnDemandPosition.isGrassExists()) {
                        strongestParents.get(1).eatGrass(fieldOnDemandPosition.getGrass().getEnergy());
                        fieldOnDemandPosition.removeElement(fieldOnDemandPosition.getGrass());
                    }

                    if (strongestParents.get(0).getEnergy() > minEnergyToReproduce && strongestParents.get(1).getEnergy() > minEnergyToReproduce) {
                        Animal childAnimal = strongestParents.get(0).reproduce(strongestParents.get(1), this, demandPosition);
                        fieldOnDemandPosition.addElement(childAnimal);
                    }
                } else {
                    if (fieldOnDemandPosition.isGrassExists()) {
                        animal.eatGrass(fieldOnDemandPosition.getGrass().getEnergy());
                        fieldOnDemandPosition.removeElement(fieldOnDemandPosition.getGrass());
                    }
                    fieldOnDemandPosition.addElement(animal);
                }
            } else {
                moveAnimal(demandPosition, animal);
            }

            return demandPosition;
        }

        return position;
    }

    public void removePosition(Animal animal, Vector2d position) {
        Field field = findField(position);
        field.removeElement(animal);
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

    public Field findField(Vector2d position) {
        return map.get(position);
    }

    public void place(Animal animal) {
        Field field = findField(animal.getPosition());
        if (!isAnimalOccupied(animal.getPosition())) {
            if (isGrassOccupied(animal.getPosition())) {
                animal.eatGrass(field.getGrass().getEnergy());
            }

            map.put(animal.getPosition(), new Field(animal));
        }
    }

    public void placeGrassOnRandomPosition() {
        Random rand = new Random();
        Vector2d position = new Vector2d(rand.nextInt(this.width), rand.nextInt(this.height));

        Grass grass = new Grass(isInJungleRange(position) ? GrassType.JUNGLE : GrassType.STEPPE);

        if (!isOccupied(position)) {
            map.put(position, new Field(grass));
        } else {
            Field field = findField(position);

            if (!field.isGrassExists()) {
                field.addElement(grass);
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
}
