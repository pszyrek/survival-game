package agh.cs.sg;

import java.util.*;

public class Animal extends MapElement implements IMapElement {
    private final Random rand = new Random();
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d pos;
    private int energy;
    private List<Integer> genes;
    private Map<Integer, Integer> calculatedGenesProbabilities;

    private IPositionChangeObserver observer = null;

    public Animal(Vector2d initialPosition, IPositionChangeObserver observer) {
        this.pos = initialPosition;
        this.energy = 20;
        this.observer = observer;

        this.genes = generateGenes();
        this.calculatedGenesProbabilities = calculateGenesProbabilities(this.genes);
    }

    public Animal(Vector2d initialPosition, IPositionChangeObserver observer, List<Integer> genes, int energy) {
        this.pos = initialPosition;
        this.energy = energy;
        this.observer = observer;

        this.genes = genes;
        this.calculatedGenesProbabilities = calculateGenesProbabilities(this.genes);
    }

    private List<Integer> generateGenes() {
        List<Integer> generatedGenes = new ArrayList<>(32);

        for(int i = 0; i < 8; i++) {
            generatedGenes.add(i);
        }

        for(int i = 0; i < 24; i++) {
            int randGene = rand.nextInt(8);
            generatedGenes.add(randGene);
        }

        Collections.sort(generatedGenes);

        return generatedGenes;
    }

    private List<Integer> completeGenes(Map<Integer, Integer> occurrencesOfGenes) {
        List<Integer> neededGenes = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        List<Integer> genes = new ArrayList<>();

        for (int neededGene : neededGenes) {
            if(!occurrencesOfGenes.containsKey(neededGene)) {
                int highestOccurrenceGene = 0;
                int highestOccurrence = 0;

                for(int gene : occurrencesOfGenes.keySet()) {
                    if(occurrencesOfGenes.get(gene) > highestOccurrence) {
                        highestOccurrenceGene = gene;
                    }
                }

                occurrencesOfGenes.put(highestOccurrenceGene, occurrencesOfGenes.get(highestOccurrenceGene) - 1);
                occurrencesOfGenes.put(neededGene, 1);
            }
        }

        for(int gene : occurrencesOfGenes.keySet()) {
            for(int i = 0; i < occurrencesOfGenes.get(gene); i++) {
                genes.add(gene);
            }
        }

        return genes;
    }

    private Map<Integer, Integer> calculateGenesProbabilities(List<Integer> genes) {
        Map<Integer, Integer> genesProbabilities  = new HashMap<>(8);
        for(Integer gene : genes) {
            if(!genesProbabilities.containsKey(gene)) {
                genesProbabilities.put(gene, 1);
            }

            genesProbabilities.put(gene, genesProbabilities.get(gene) + 1);
        }

        for(Integer key : genesProbabilities.keySet()) {
            Integer probability = (int) (((float) genesProbabilities.get(key) / (float) this.genes.size()) * 100);
            genesProbabilities.put(key, probability);
        }

        return genesProbabilities;
    }

    private int generateMove() {
        Integer maxValue = Collections.max(this.calculatedGenesProbabilities.values());

        Integer randProbability = rand.nextInt(maxValue - 1) + 1;

        List<Integer> probabilities = new ArrayList<>();

        for(Integer key : this.calculatedGenesProbabilities.keySet()) {
            if(this.calculatedGenesProbabilities.get(key) >= randProbability) {
                probabilities.add(key);
            }
        }

        Integer probabilityMove = probabilities.get(rand.nextInt(probabilities.size()));

        return probabilityMove;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void addEnergy(int energyLevel) {
        this.energy += energyLevel;
    }

    public void decreaseEnergyForMove() {
        this.energy -= 1;
    }

    public int decreaseEnergyForReproduce() {
        int decreasingEnergy = (this.energy / 4);
        this.energy -= this.energy - decreasingEnergy;

        return decreasingEnergy ;
    }

    private int getParentsEnergy(Animal animalParent) {
        int parentsEnergy = this.decreaseEnergyForReproduce() + animalParent.decreaseEnergyForReproduce();

        return parentsEnergy;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return pos;
    }

    public void move() {
        int move = generateMove();

        if(this.energy > 0) {
            if(move > 0) {
                this.orientation = orientation.changeDirection(move, this.orientation);
                return;
            }

            if(this.observer != null) {
                Vector2d newPosition = observer.positionChange(this, this.pos);
                this.pos = newPosition;
            }

            this.decreaseEnergyForMove();
        } else {
            if(this.observer != null) {
                this.observer.removePosition(this, this.pos);
            }
        }
    }

    private List<Integer> rewriteGenesFromParents(int splitingIndex, List<Integer> animalParentGenes) {
        Map<Integer, Integer> occurenciesOfGenes = new HashMap<>();

        for(int i = 0; i < splitingIndex; i++) {
            int number = this.genes.get(i);
            if(occurenciesOfGenes.containsKey(number)) {
                occurenciesOfGenes.put(number, occurenciesOfGenes.get(number) + 1);
            } else {
                occurenciesOfGenes.put(number, 1);
            }
        }

        for(int i = splitingIndex; i < 32; i++) {
            int number = animalParentGenes.get(i);
            if(occurenciesOfGenes.containsKey(number)) {
                occurenciesOfGenes.put(number, occurenciesOfGenes.get(number) + 1);
            } else {
                occurenciesOfGenes.put(number, 1);
            }
        }

        return completeGenes(occurenciesOfGenes);
    }

    public List<Integer> generateGenesFromParents(Animal animalParent) {
        int randIndex1 = rand.nextInt(31);
        int randIndex2 = rand.nextInt(31);

        while(randIndex1 == randIndex2) {
            randIndex2 = rand.nextInt(31);
        }

        if(randIndex1 < randIndex2) {
            return rewriteGenesFromParents(randIndex2, animalParent.genes);
        }

        return rewriteGenesFromParents(randIndex1, animalParent.genes);
    }

    public Animal reproduce(Animal animalParent, IPositionChangeObserver world, Vector2d position) {
        int childAnimalEnergy = this.getParentsEnergy(animalParent);
        Animal childAnimal = new Animal(position, world, generateGenesFromParents(animalParent), childAnimalEnergy);

        System.out.println(childAnimal.getEnergy());

        return childAnimal;
    }

    public void eatGrass() {
        addEnergy(4);
    }

    @Override
    public String toString() {
        char orientationChar = orientation.toString().charAt(0);
        return String.valueOf(orientationChar);
    }
}
