package agh.cs.sg;


import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap {
    RectangularMap(int width, int height) {
        this.upperRight = new Vector2d(width, height);
    }

    @Override
    public void eatGrass(Vector2d position) {

    }
}
