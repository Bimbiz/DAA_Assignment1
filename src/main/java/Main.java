import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage:");
            System.out.println("  java Main <algorithm> <n> [k]");
            System.out.println("Algorithms: mergesort, quicksort, select, closest");
            System.out.println("Example: java Main mergesort 10000");
            System.out.println("Example: java Main select 10000 5000");
            return;
        }

        String algorithm = args[0].toLowerCase();
        int n = Integer.parseInt(args[1]);

        // Reset metrics before each run
        Metrics.reset();
        Metrics.startTimer();

        switch (algorithm) {
            case "mergesort" -> runMergeSort(n);
            case "quicksort" -> runQuickSort(n);
            case "select" -> {
                if (args.length < 3) {
                    System.out.println("Error: provide k for select (0-based index)");
                    return;
                }
                int k = Integer.parseInt(args[2]);
                runSelect(n, k);
            }
            case "closest" -> runClosestPair(n);
            default -> {
                System.out.println("Unknown algorithm: " + algorithm);
                return;
            }
        }

        Metrics.stopTimer();
        Metrics.writeToCSV(algorithm, n, "results.csv");

        System.out.println("Run completed. Metrics:");
        System.out.println("  Comparisons: " + Metrics.getComparisons());
        System.out.println("  Allocations: " + Metrics.getAllocations());
        System.out.println("  Max recursion depth: " + Metrics.getMaxDepth());
        System.out.println("  Time (ms): " + Metrics.getElapsedMillis());
    }

    private static void runMergeSort(int n) {
        int[] arr = generateRandomArray(n);
        MergeSort.sort(arr);
    }

    private static void runQuickSort(int n) {
        int[] arr = generateRandomArray(n);
        QuickSort.sort(arr);
    }

    private static void runSelect(int n, int k) {
        int[] arr = generateRandomArray(n);
        int result = DeterministicSelect.select(arr, k);
        System.out.println("Selected element at index " + k + ": " + result);
    }

    private static void runClosestPair(int n) {
        Point[] points = generateRandomPoints(n);
        double dist = ClosestPair.findClosestDistance(points);
        System.out.println("Closest pair distance: " + dist);
    }

    // --- Helpers ---
    private static int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] arr = new Random().ints(n, 0, 1_000_000).toArray();
        Metrics.incrementAllocations(); // массив создан
        return arr;
    }

    private static Point[] generateRandomPoints(int n) {
        Random rand = new Random();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }
        Metrics.incrementAllocations(); // массив создан
        return points;
    }
}
