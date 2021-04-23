import agh.cs.sg.MapDirection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapDirectionTest {
    @Test public void nextTest() {
        MapDirection md = MapDirection.NORTH;
        assertEquals(md.next(), MapDirection.EAST);
    }

    @Test public void previousTest() {
        MapDirection md = MapDirection.NORTH;
        assertEquals(md.previous(), MapDirection.WEST);
    }
}
