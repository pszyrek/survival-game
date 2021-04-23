package agh.cs.sg;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d pos;
    private IWorldMap map;

    Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.pos = initialPosition;
    }

    public Vector2d getPosition() {
        return pos;
    }

    public void move(MoveDirection direction) {
        if(direction == MoveDirection.RIGHT) {
            orientation = orientation.next();
        }

        if(direction == MoveDirection.LEFT) {
            orientation = orientation.previous();
        }

        if(direction == MoveDirection.FORWARD || direction == MoveDirection.BACKWARD) {
            if(map.canMoveTo(pos.add(orientation.toUnitVector()))) {
                pos = pos.add(orientation.toUnitVector());
            }
        }
    }

    @Override
    public String toString() {
        char orientationChar = orientation.toString().charAt(0);
        return String.valueOf(orientationChar);
    }
}
