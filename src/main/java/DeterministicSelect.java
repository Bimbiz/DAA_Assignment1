public class DeterministicSelect {

    public static int select(int[] arr, int k) {
        return select(arr, 0, arr.length - 1, k);
    }

    private static int select(int[] arr, int left, int right, int k) {
        if (left == right) return arr[left];

        // Find median of medians as pivot
        int pivotValue = medianOfMedians(arr, left, right);

        // Find pivot index and move to end
        int pivotIndex = findAndMovePivot(arr, left, right, pivotValue);

        // In-place partition
        int partitionIndex = partition(arr, left, right, pivotIndex);

        // Recurse only into needed side (prefer smaller side)
        if (k == partitionIndex) {
            return arr[k];
        } else if (k < partitionIndex) {
            return select(arr, left, partitionIndex - 1, k);
        } else {
            return select(arr, partitionIndex + 1, right, k);
        }
    }

    // Group by 5, find median of medians
    private static int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(arr, left, right);
            return arr[left + n/2];
        }

        // Create array of medians
        int numGroups = (n + 4) / 5; // Ceiling division
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int groupLeft = left + i * 5;
            int groupRight = Math.min(groupLeft + 4, right);

            insertionSort(arr, groupLeft, groupRight);
            medians[i] = arr[groupLeft + (groupRight - groupLeft) / 2];
        }

        // Recursively find median of medians
        return select(medians, 0, medians.length - 1, medians.length / 2);
    }

    private static int findAndMovePivot(int[] arr, int left, int right, int pivotValue) {
        for (int i = left; i <= right; i++) {
            if (arr[i] == pivotValue) {
                swap(arr, i, right);
                return right;
            }
        }
        return right; // Fallback
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);

        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivotValue) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}