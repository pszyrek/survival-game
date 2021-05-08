package agh.cs.sg.grass;

import agh.cs.sg.MapElement;

public class Grass extends MapElement {
    private GrassType type;

    public Grass(GrassType type) {
        this.type = type;
    }

    public Grass() {
        this.type = GrassType.STEPPE;
    }

    @Override
    public String toString() {
        return "*";
    }
}
