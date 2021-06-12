package agh.cs.sg;

import agh.cs.sg.grass.Grass;
import agh.cs.sg.grass.GrassType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World implements IPositionChangeObserver {
    private final int GRASS_SIZE = GameConfiguration.grassSize;
    private final int width;
    private final int height;

    private final Map<Vector2d, Field> map = new HashMap<>();
    private List deadAnimalsList = new ArrayList();

    private final Vector2d upperRightMapCorner;
    private final Vector2d lowerLeftMapCorner = new Vector2d(0, 0);

    private Vector2d lowerLeftJungleCorner;
    private Vector2d upperRightJungleCorner;

    private int era = 0;
    private int valueOfAnimalsEnergy = 0;
    private int numberOfAnimals = 0;
    private int numberOfGrass = 0;
    private int numberOfChildren = 0;
    private int lifeTimeForDeadAnimals = 0;

    private final int minEnergyToReproduce;

    private Map<Integer, Integer> dominantGenesCounter = new HashMap<>(8);

    public World(int width, int height, int minEnergyToReproduce) {
        this.upperRightMapCorner = new Vector2d(width, height);
        this.width = width;
        this.height = height;

        this.minEnergyToReproduce = minEnergyToReproduce;

        setJungle();

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
                        Vector2d randomNextToPosition = demandPosition.getRandomNextToPosition();
                        Animal childAnimal = strongestParents.get(0).reproduce(strongestParents.get(1), this, randomNextToPosition);
                        childAnimal.setNumberOfDescendants(strongestParents.get(0).getNumberOfDescendants() + strongestParents.get(1).getNumberOfDescendants() + 2);

                        if(map.containsKey(randomNextToPosition)) {
                            Field fieldForRandomNextToPosition = findField(randomNextToPosition);
                            fieldForRandomNextToPosition.addElement(childAnimal);
                        } else {
                            this.place(childAnimal);
                        }
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

    public void updateDominantGenes(int dominantGene) {
        if(this.dominantGenesCounter.containsKey(dominantGene)) {
            this.dominantGenesCounter.put(dominantGene, this.dominantGenesCounter.get(dominantGene) + 1);
        } else {
            this.dominantGenesCounter.put(dominantGene, 1);
        }
    }

    public int getDominantGeneNumber(int dominantGene) {
        return this.dominantGenesCounter.get(dominantGene);
    }

    public void resetDominantGeneCounter() {
        for(int gene : this.dominantGenesCounter.keySet()) {
            this.dominantGenesCounter.put(gene, 0);
        }
    }

    public void removePosition(Animal animal, Vector2d position) {
        this.deadAnimalsList.add(animal);

        Field field = findField(position);
        field.removeElement(animal);
    }

    public Map<Vector2d, Field> getMap() {
        return this.map;
    }

    public boolean isGrassOccupied(Vector2d position) {
        return map.containsKey(position) && map.get(position).isGrassExists();
    }

    public Integer numberOfAnimalsInField(Vector2d position) {
        return findField(position).getAnimals().size();
    }

    public boolean isAnimalOccupied(Vector2d position) {
        return map.containsKey(position) && map.get(position).isAnimalExists();
    }

    public boolean isOccupied(Vector2d position) {
        return isAnimalOccupied(position) || isGrassOccupied(position);
    }

    public Field findField(Vector2d position) {
        if(map.containsKey(position)) {
            return map.get(position);
        }

        return null;
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

            if (!field.isGrassExists() && !field.isAnimalExists()) {
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

    public void setJungle() {
        int sizeOfJungle = (int) (this.height * ((float)GameConfiguration.jungleRatio / 100.0));
        int lowerX = ((this.width / 2) - (sizeOfJungle / 2));
        int upperX = ((this.width / 2) + (sizeOfJungle / 2));

        int lowerY = ((this.height / 2) - (sizeOfJungle / 2));
        int upperY = ((this.height / 2) + (sizeOfJungle / 2));

        this.lowerLeftJungleCorner = new Vector2d(lowerX, lowerY);
        this.upperRightJungleCorner = new Vector2d(upperX, upperY);
    }

    public void increaseEra() {
        this.era += 1;
    }

    public int getEra() {
        return this.era;
    }

    public void increaseNumberOfAnimals() {
        this.numberOfAnimals += 1;
    }

    public void resetNumberOfAnimals() {
        this.numberOfAnimals = 0;
    }

    public int getNumberOfAnimals() {
        return this.numberOfAnimals;
    }

    public void increaseValueOfAnimalsEnergy(int energy) {
        this.valueOfAnimalsEnergy += energy;
    }

    public void resetValueOfAnimalsEnergy() {
        this.valueOfAnimalsEnergy = 0;
    }

    public int getValueOfAnimalsEnergy() {
        return this.valueOfAnimalsEnergy;
    }

    public void increaseNumberOfGrass() {
        this.numberOfGrass += 1;
    }

    public int getNumberOfGrass() {
        return this.numberOfGrass;
    }

    public void resetNumberOfGrass() {
        this.numberOfGrass = 0;
    }

    public void increaseNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren += numberOfChildren;
    }

    public int getNumberOfChildren() {
        return this.numberOfChildren;
    }

    public void resetNumberOfChildren() {
        this.numberOfChildren = 0;
    }

    public List<Animal> getDeadAnimalsList() {
        return this.deadAnimalsList;
    }

    public void increaseLifeTimeForDeadAnimals(int lifeTime) {
        this.lifeTimeForDeadAnimals += lifeTime;
    }

    public void resetLifeTimeForDeadAnimals() {
        this.lifeTimeForDeadAnimals = 0;
    }

    public int getLifeTimeForDeadAnimals() {
        return this.lifeTimeForDeadAnimals;
    }
}
