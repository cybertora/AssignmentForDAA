import java.util.Arrays;

public class ClosestPair {
    private final Metrics metrics;

    public ClosestPair(Metrics metrics) {
        this.metrics = metrics;
    }

    public double findClosestPair(Point[] points) {
        metrics.reset();
        long startTime = System.nanoTime();
        Arrays.sort(points);
        double minDist = closestPairHelper(points, 0, points.length - 1);
        metrics.executionTime = System.nanoTime() - startTime;
        return minDist;
    }

    private double closestPairHelper(Point[] points, int low, int high) {
        metrics.recursionDepth = Math.max(metrics.recursionDepth, metrics.recursionDepth + 1);

        if (high - low <= 3) {
            return bruteForceClosest(points, low, high);
        }

        int mid = low + (high - low) / 2;
        Point midPoint = points[mid];

        double leftMin = closestPairHelper(points, low, mid);
        double rightMin = closestPairHelper(points, mid + 1, high);
        double delta = Math.min(leftMin, rightMin);

        Point[] strip = new Point[high - low + 1];
        int stripSize = 0;
        for (int i = low; i <= high; i++) {
            if (Math.abs(points[i].x - midPoint.x) < delta) {
                strip[stripSize++] = points[i];
            }
        }

        Arrays.sort(strip, 0, stripSize, (a, b) -> Double.compare(a.y, b.y));

        double minDistInStrip = delta;
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (strip[j].y - strip[i].y) < minDistInStrip; j++) {
                metrics.comparisons++;
                double dist = distance(strip[i], strip[j]);
                if (dist < minDistInStrip) {
                    minDistInStrip = dist;
                }
                metrics.distanceCalculations++;
            }
        }

        return Math.min(delta, minDistInStrip);
    }

    public double bruteForceClosestPair(Point[] points) {
        Metrics bruteMetrics = new Metrics();
        long startTime = System.nanoTime();
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                bruteMetrics.comparisons++;
                double dist = distance(points[i], points[j]);
                if (dist < min) {
                    min = dist;
                }
                bruteMetrics.distanceCalculations++;
            }
        }
        bruteMetrics.executionTime = System.nanoTime() - startTime;
        System.out.println("Brute Force Metrics: " + bruteMetrics);
        return min;
    }

    private double bruteForceClosest(Point[] points, int low, int high) {
        double min = Double.MAX_VALUE;
        for (int i = low; i <= high; i++) {
            for (int j = i + 1; j <= high; j++) {
                metrics.comparisons++;
                double dist = distance(points[i], points[j]);
                if (dist < min) {
                    min = dist;
                }
                metrics.distanceCalculations++;
            }
        }
        return min;
    }

    private double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}