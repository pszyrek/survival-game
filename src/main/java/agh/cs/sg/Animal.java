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

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if(observer != null) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public Vector2d getPosition() {
        return pos;
    }

    public void move(Integer direction) {
        if(direction > 0) {
            orientation = orientation.changeDirection(direction);
        }

        if(map.canMoveTo(pos.add(orientation.toUnitVector()))) {
            positionChanged(pos, pos.add(orientation.toUnitVector()));
            pos = pos.add(orientation.toUnitVector());
        }

        map.eatGrass(pos);
    }

    @Override
    public String toString() {
        char orientationChar = orientation.toString().charAt(0);
        return String.valueOf(orientationChar);
    }
}
