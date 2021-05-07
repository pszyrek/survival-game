package agh.cs.sg;

public class RectangularMap extends AbstractWorldMap implements IPositionChangeObserver {
    RectangularMap(int width, int height) {
        this.upperRight = new Vector2d(width, height);
    }

    @Override
    public void eatGrass(Vector2d position) {

    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }
}
