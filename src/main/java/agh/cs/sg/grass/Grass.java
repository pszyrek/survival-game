package agh.cs.sg.grass;

import agh.cs.sg.MapElement;

public class Grass extends MapElement {
    private final GrassType type;
    private final int energy;

    public Grass(GrassType type) {
        this.type = type;
        this.energy = type.getEnergy();
    }

    public Grass() {
        GrassType grassType = GrassType.STEPPE;
        this.type = grassType;
        this.energy = grassType.getEnergy();
    }

    public int getEnergy() {
        return this.energy;
    }

    public GrassType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type == GrassType.JUNGLE ? "J" : "S";
    }
}
