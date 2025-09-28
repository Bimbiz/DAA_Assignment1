import java.util.Random;

public class QuickSort {
    private static Random rand = new Random();

    public static void sort(int[] arr) {
        if (arr.length <= 1) return;
        shuffle(arr); // Initial randomization for robustness
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        while (left < right) {
            // Randomized pivot selection
            int randomIndex = left + rand.nextInt(right - left + 1);
            swap(arr, randomIndex, right);

            int pivot = partition(arr, left, right);

            // Recurse on smaller partition, iterate on larger (bounded stack)
            if (pivot - left < right - pivot) {
                quickSort(arr, left, pivot - 1);  // Smaller partition
                left = pivot + 1;                 // Iterate on larger
            } else {
                quickSort(arr, pivot + 1, right); // Smaller partition
                right = pivot - 1;                // Iterate on larger
            }
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private static void shuffle(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(arr, i, j);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}