package agh.cs.sg;

public class Animal implements IMapElement {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d pos;
    private IWorldMap map;
    private int energy;

    IPositionChangeObserver observer = null;

    Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.pos = initialPosition;
        this.energy = 0;
    }

    public void addObserver(IPositionChangeObserver observer) {
        this.observer = observer;
    }

    public void addEnergy(int energyLevel) {
        this.energy += energyLevel;
    }

    public void removeObserver() {
        this.observer = null;
    }

    public Vector2d getPosition() {
        return pos;
    }

    public void move(Integer direction) {
        if(direction > 0) {
            this.orientation = orientation.changeDirection(direction);
            return;
        }

        if(map.canMoveTo(this.pos.add(orientation.toUnitVector()))) {
            Vector2d newPos = this.pos.add(orientation.toUnitVector());
            observer.positionChanged(this.pos, newPos);
            this.pos = newPos;
        }


        if(map.isGrassOccupied(this.pos)) {
            map.eatGrass(this.pos);
        };
    }

    @Override
    public String toString() {
        char orientationChar = orientation.toString().charAt(0);
        return String.valueOf(orientationChar);
    }
}
