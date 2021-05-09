package agh.cs.sg;

import java.util.*;

public class Animal extends MapElement implements IMapElement {
    private final Random rand = new Random();
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d pos;
    private int energy;
    private List<Integer> genes;
    private Map<Integer, Integer> calculatedGenesProbabilities;

    private int MAP_DIRECTION = 0;

    private IPositionChangeObserver observer = null;

    public Animal(Vector2d initialPosition, IPositionChangeObserver observer) {
        this.pos = initialPosition;
        this.energy = 0;
        this.observer = observer;

        this.genes = generateGenes();
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

        System.out.println(probabilities);

        Integer probabilityMove = probabilities.get(rand.nextInt(probabilities.size()));

        System.out.println(probabilityMove);

        return probabilityMove;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void addEnergy(int energyLevel) {
        this.energy += energyLevel;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return pos;
    }

    public void move() {
        int move = generateMove();

        if(move > 0) {
            this.orientation = orientation.changeDirection(move, this.orientation);
            return;
        }

        if(this.observer != null) {
            Vector2d newPosition = observer.positionChange(this, this.pos);
            this.pos = newPosition;
        }
    }

    public void eatGrass() {
        addEnergy(1);
    }

    @Override
    public String toString() {
        char orientationChar = orientation.toString().charAt(0);
        return String.valueOf(orientationChar);
    }
}
