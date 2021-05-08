package agh.cs.sg;

public class Animal extends MapElement implements IMapElement {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d pos;
    private int energy;

    private IPositionChangeObserver observer = null;

    public Animal(Vector2d initialPosition, IPositionChangeObserver observer) {
        this.pos = initialPosition;
        this.energy = 0;
        this.observer = observer;
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

    public void move(Integer direction) {
        if(direction > 0) {
            this.orientation = orientation.changeDirection(direction);
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
