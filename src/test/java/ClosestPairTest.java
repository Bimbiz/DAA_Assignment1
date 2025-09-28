import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {

    @Test
    void testSimpleCase() {
        Point[] points = {
                new Point(0, 0),
                new Point(3, 4),
                new Point(7, 7),
                new Point(1, 1)
        };

        double result = ClosestPair.findClosestDistance(points);
        double expected = Math.sqrt(2); // расстояние между (0,0) и (1,1)

        assertEquals(expected, result, 1e-6);
    }

    @Test
    void testTwoPoints() {
        Point[] points = {
                new Point(0, 0),
                new Point(5, 5)
        };

        double result = ClosestPair.findClosestDistance(points);
        assertEquals(Math.sqrt(50), result, 1e-6);
    }

    @Test
    void testDuplicatePoints() {
        Point[] points = {
                new Point(2, 3),
                new Point(2, 3),
                new Point(10, 10)
        };

        double result = ClosestPair.findClosestDistance(points);
        assertEquals(0.0, result, 1e-6);
    }
}
