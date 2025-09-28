import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Metrics {
    private static long comparisons = 0;
    private static long allocations = 0;
    private static int currentDepth = 0;
    private static int maxDepth = 0;
    private static long startTime = 0;
    private static long endTime = 0;

    // --- Reset before each run ---
    public static void reset() {
        comparisons = 0;
        allocations = 0;
        currentDepth = 0;
        maxDepth = 0;
        startTime = 0;
        endTime = 0;
    }

    // --- Comparisons ---
    public static void incrementComparisons() {
        comparisons++;
    }

    public static long getComparisons() {
        return comparisons;
    }

    // --- Allocations (например, копия массива или создание буфера) ---
    public static void incrementAllocations() {
        allocations++;
    }

    public static long getAllocations() {
        return allocations;
    }

    // --- Depth tracking ---
    public static void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public static void exitRecursion() {
        currentDepth--;
    }

    public static int getMaxDepth() {
        return maxDepth;
    }

    // --- Timing ---
    public static void startTimer() {
        startTime = System.nanoTime();
    }

    public static void stopTimer() {
        endTime = System.nanoTime();
    }

    public static double getElapsedMillis() {
        if (startTime == 0 || endTime == 0) return 0.0;
        return (endTime - startTime) / 1_000_000.0;
    }

    // --- CSV Writer ---
    public static void writeToCSV(String algorithm, int n, String filename) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))) {
            // header если файл пустой
            if (new java.io.File(filename).length() == 0) {
                out.println("timestamp,algorithm,n,comparisons,allocations,maxDepth,timeMillis");
            }
            out.printf("%s,%s,%d,%d,%d,%d,%.3f%n",
                    LocalDateTime.now(),
                    algorithm,
                    n,
                    comparisons,
                    allocations,
                    maxDepth,
                    getElapsedMillis()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
