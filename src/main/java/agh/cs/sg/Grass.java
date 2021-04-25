package agh.cs.sg;

public class Grass implements IMapElement {
    Vector2d currentPosition;

    Grass(Vector2d position) {
        this.currentPosition = position;
    }

    public Vector2d getPosition() {
        return currentPosition;
    }

    public String toString() {
        return "*";
    }
}
