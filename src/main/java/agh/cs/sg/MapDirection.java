package agh.cs.sg;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public String toString(){
        String direction = switch(this) {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
            case EAST -> "Wschód";
        };

        return direction;
    }

    public MapDirection next() {
        MapDirection changedDirection = switch(this) {
            case NORTH -> EAST;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            case EAST -> SOUTH;
        };

        return changedDirection;
    }

    public MapDirection previous() {
        MapDirection changedDirection = switch(this) {
            case NORTH -> WEST;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
            case EAST -> NORTH;
        };

        return changedDirection;
    }

    public Vector2d toUnitVector() {
        Vector2d vector = switch(this) {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case EAST -> new Vector2d(1, 0);
        };

        return vector;
    }
}
