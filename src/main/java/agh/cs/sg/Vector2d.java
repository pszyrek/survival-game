package agh.cs.sg;

import java.util.Objects;
import java.util.Random;

public class Vector2d { // opisać vector2d
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        int x, y;

        x = Math.max(this.x, other.x);

        y = Math.max(this.y, other.y);

        return new Vector2d(x, y);
    }

    public Vector2d lowerRight(Vector2d other) {
        int x, y;

        x = Math.min(this.x, other.x);

        y = Math.min(this.y, other.y);

        return new Vector2d(x, y);
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other){
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector2d)) {
            return false;
        }

        Vector2d that = (Vector2d) other;

        return this.x == that.x && this.y == that.y;
    }

    public Vector2d opposite() {
        return new Vector2d(-(this.x), -(this.y));
    }

    public Vector2d getRandomNextToPosition() {
        Random rand = new Random();

        int minX = this.x - 1;
        int maxX = this.x + 1;

        int minY = this.y - 1;
        int maxY = this.y + 1;

        Vector2d demandPosition = this;

        while(demandPosition == this) {
            int randX = rand.nextInt(maxX - minX + 1) + minX;
            int randY = rand.nextInt(maxY - minY + 1) + minY;

            demandPosition = new Vector2d(randX, randY);
        }

        return demandPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
