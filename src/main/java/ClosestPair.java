import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClosestPair {

    public static double findClosestDistance(Point[] points) {
        if (points.length < 2) return Double.MAX_VALUE;

        // Sort by x-coordinate
        Point[] sortedByX = points.clone();
        Arrays.sort(sortedByX, (p1, p2) -> Double.compare(p1.x, p2.x));

        // Also prepare y-sorted array for strip operations
        Point[] sortedByY = points.clone();
        Arrays.sort(sortedByY, (p1, p2) -> Double.compare(p1.y, p2.y));

        return closestRec(sortedByX, sortedByY);
    }

    private static double closestRec(Point[] sortedByX, Point[] sortedByY) {
        int n = sortedByX.length;

        // Base case: brute force for small arrays
        if (n <= 3) {
            return bruteForce(sortedByX);
        }

        // Recursive split
        int mid = n / 2;
        Point midPoint = sortedByX[mid];

        Point[] leftByX = Arrays.copyOfRange(sortedByX, 0, mid);
        Point[] rightByX = Arrays.copyOfRange(sortedByX, mid, n);

        // Split sortedByY based on midpoint
        Point[] leftByY = new Point[mid];
        Point[] rightByY = new Point[n - mid];

        int leftIdx = 0, rightIdx = 0;
        for (Point p : sortedByY) {
            if (p.x <= midPoint.x && leftIdx < mid) {
                leftByY[leftIdx++] = p;
            } else {
                rightByY[rightIdx++] = p;
            }
        }

        double leftMin = closestRec(leftByX, leftByY);
        double rightMin = closestRec(rightByX, rightByY);
        double minDist = Math.min(leftMin, rightMin);

        // Strip check by y order (classic 7-8 neighbor scan)
        return Math.min(minDist, stripClosest(sortedByY, midPoint.x, minDist));
    }

    private static double stripClosest(Point[] sortedByY, double midX, double minDist) {
        List<Point> strip = new ArrayList<>();

        for (Point p : sortedByY) {
            if (Math.abs(p.x - midX) < minDist) {
                strip.add(p);
            }
        }

        double stripMin = minDist;

        // Classic 7-8 neighbor scan
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && j < i + 8; j++) {
                Point p1 = strip.get(i);
                Point p2 = strip.get(j);

                if (p2.y - p1.y >= stripMin) break; // Early termination

                double dist = p1.distanceTo(p2);
                stripMin = Math.min(stripMin, dist);
            }
        }

        return stripMin;
    }

    private static double bruteForce(Point[] points) {
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = points[i].distanceTo(points[j]);
                minDist = Math.min(minDist, dist);
            }
        }
        return minDist;
    }
}
