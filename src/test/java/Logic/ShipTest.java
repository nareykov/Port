package Logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by narey on 21.03.2017.
 */
class ShipTest {
    Ship ship = new Ship(0,0,0,0,0);
    @Test
    void setDuration() {
        ship.setDuration(1);
        assertEquals(1, ship.getDuration());
    }

    @Test
    void setLoading() {
        ship.setLoading(1);
        assertEquals(1, ship.getLoading());
    }

    @Test
    void setUnloading() {
        ship.setUnloading(1);
        assertEquals(1, ship.getUnloading());
    }

}