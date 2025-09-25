public class Metrics {
    long comparision;
    long swaps;
    long distanceCalculation;
    int recursionDepth;
    long executionTime;

    public Metrics() {
        reset();
    }

    public void reset() {
        comparision = 0;
        swaps = 0;
        distanceCalculation = 0;
        recursionDepth = 0;
        executionTime = 0;
    }

    @Override
    public String toString() {
        return "Comparisons: " + comparisons + ", Swaps: " + swaps +
                ", Distance Calcs: " + distanceCalculations +
                ", Max Recursion Depth: " + recursionDepth +
                ", Execution Time (ns): " + executionTime;
    }
}
