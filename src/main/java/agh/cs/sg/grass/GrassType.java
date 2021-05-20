package agh.cs.sg.grass;

public enum GrassType {
    JUNGLE,
    STEPPE;

    public int getEnergy() {
        return switch(this) {
            case STEPPE -> 4;
            case JUNGLE -> 8;
        };
    }
}
