package agh.cs.sg;

public class Animal extends MapElement implements IMapElement {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d pos;
    private World map;
    private int energy;

    public Animal(World map, Vector2d initialPosition) {
        this.map = map;
        this.pos = initialPosition;
        this.energy = 0;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void addEnergy(int energyLevel) {
        this.energy += energyLevel;
    }

    public Vector2d getPosition() {
        return pos;
    }

    public void move(Integer direction) {
        if(direction > 0) {
            this.orientation = orientation.changeDirection(direction);
            return;
        }

        Vector2d demandPosition = this.pos.add(orientation.toUnitVector());
        if(map.isInMapRange(demandPosition)) {

            if(map.isAnimalOccupied(demandPosition)) {

            } else {
                this.pos = demandPosition;
                map.place(this);
            }
        }

        if(map.isGrassOccupied(this.pos)) {
            eatGrass();
            map.removeGrass(this.pos);
        }
    }

    private void eatGrass() {
        addEnergy(1);
    }

    @Override
    public String toString() {
        char orientationChar = orientation.toString().charAt(0);
        return String.valueOf(orientationChar);
    }
}
