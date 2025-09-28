import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    @Test
    void testRandomArray() {
        int[] arr = {10, 7, 8, 9, 1, 5};
        int[] expected = arr.clone();
        Arrays.sort(expected);

        QuickSort.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        QuickSort.sort(arr);
        assertEquals(42, arr[0]);
    }

    @Test
    void testWithDuplicates() {
        int[] arr = {4, 2, 4, 3, 2};
        int[] expected = arr.clone();
        Arrays.sort(expected);

        QuickSort.sort(arr);

        assertArrayEquals(expected, arr);
    }
}
