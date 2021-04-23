import agh.cs.sg.Vector2d;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Vector2dTest {
    Vector2d v2d = new Vector2d(2, 2);

    @Test public void equalsTest() {
        Vector2d v2dTest = new Vector2d(2, 2);

        assertEquals(v2d.equals(v2dTest), true);
    }

    @Test public void toStringTest() {
        assertEquals(v2d.toString(), "(2,2)");
    }

    @Test public void precedesTest() {
        Vector2d v2dTest = new Vector2d(3, 3);
        assertEquals(v2d.precedes(v2dTest), true);
    }

    @Test public void followsTest() {
        Vector2d v2dTest = new Vector2d(1, 1);
        assertEquals(v2d.follows(v2dTest), true);
    }

    @Test public void upperRightTest() {
        Vector2d v2dTest = new Vector2d(3, 1);
        Vector2d v2dSuccess = new Vector2d(3, 2);
        assertEquals(v2dSuccess.equals(v2d.upperRight(v2dTest)), true);
    }

    @Test public void lowerRightTest() {
        Vector2d v2dTest = new Vector2d(3, 1);
        Vector2d v2dSuccess = new Vector2d(2, 1);
        assertEquals(v2dSuccess.equals(v2d.lowerRight(v2dTest)), true);
    }

    @Test public void addTest() {
        Vector2d v2dTest = new Vector2d(3, 1);
        Vector2d v2dSuccess = new Vector2d(5, 3);
        assertEquals(v2dSuccess.equals(v2d.add(v2dTest)), true);
    }

    @Test public void subtractTest() {
        Vector2d v2dTest = new Vector2d(3, 1);
        Vector2d v2dSuccess = new Vector2d(-1, 1);
        assertEquals(v2dSuccess.equals(v2d.subtract(v2dTest)), true);
    }

    @Test public void oppositeTest() {
        Vector2d v2dSuccess = new Vector2d(-2, -2);
        assertEquals(v2dSuccess.equals(v2d.opposite()), true);
    }
}
