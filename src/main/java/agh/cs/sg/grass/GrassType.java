package agh.cs.sg.grass;

import agh.cs.sg.GameConfiguration;

public enum GrassType {
    JUNGLE,
    STEPPE;

    public int getEnergy() {
        return switch(this) {
            case STEPPE -> GameConfiguration.steppeEnergyValue;
            case JUNGLE -> GameConfiguration.jungleEnergyValue;
        };
    }
}
