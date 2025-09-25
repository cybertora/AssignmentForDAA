import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Metrics metrics = new Metrics();
        MergeSort mergeSort = new MergeSort(metrics);
        QuickSort quickSort = new QuickSort(metrics);
        DeterministicSelection selection = new DeterministicSelection(metrics);
        ClosestPair closestPair = new ClosestPair(metrics);
        Random random = new Random();
        int[] sizes = {100, 1000, 5000};

        System.out.println("MERGE SORT VALIDATION");
        for (int n : sizes) {
            System.out.println("\n Size: " + n + " ");

            int[] randomArr = TestUtils.createRandomArray(n);
            int[] randomCopy = randomArr.clone();
            System.out.println("Random array test:");
            mergeSort.sort(randomArr);
            boolean isSorted = TestUtils.isSorted(randomArr);
            System.out.println("Sorted correctly: " + isSorted);
            System.out.println(metrics);

            int[] advArr = TestUtils.createAdversarialArray(n);
            int[] advCopy = advArr.clone();
            System.out.println("Adversarial array test:");
            mergeSort.sort(advArr);
            isSorted = TestUtils.isSorted(advArr);
            System.out.println("Sorted correctly: " + isSorted);
            System.out.println(metrics);
        }

        System.out.println("\n QUICK SORT VALIDATION ");
        for (int n : sizes) {
            System.out.println("\n Size: " + n + " ");

            int[] randomArr = TestUtils.createRandomArray(n);
            int[] randomCopy = randomArr.clone();
            System.out.println("Random array test:");
            quickSort.sort(randomArr);
            boolean isSorted = TestUtils.isSorted(randomArr);
            int expectedDepth = (int) (2 * Math.log(n) / Math.log(2));
            boolean depthOK = metrics.recursionDepth <= expectedDepth + 5;
            System.out.println("Sorted correctly: " + isSorted +
                    ", Depth OK (≤" + (expectedDepth + 5) + "): " + depthOK);
            System.out.println(metrics);

            int[] advArr = TestUtils.createAdversarialArray(n);
            int[] advCopy = advArr.clone();
            System.out.println("Adversarial array test:");
            quickSort.sort(advArr);
            isSorted = TestUtils.isSorted(advArr);
            depthOK = metrics.recursionDepth <= expectedDepth + 5;
            System.out.println("Sorted correctly: " + isSorted +
                    ", Depth OK (≤" + (expectedDepth + 5) + "): " + depthOK);
            System.out.println(metrics);
        }

        System.out.println( "\n Deterministic SELECTION VALIDATION ");
        for (int n : sizes) {
            System.out.println("\n Size: " + n + " ");

            int[] arr = TestUtils.createRandomArray(n);
            int[] sortedArr = arr.clone();
            Arrays.sort(sortedArr);

            int correctCount = 0;
            for (int trial = 0; trial < 100; trial++) {
                int k = rand.nextInt(n);
                int selected = selection.select(arr.clone(), k + 1);
                if (selected == sortedArr[k]) {
                    correctCount++;
                }
            }

            double accuracy = (double) correctCount / 100;
            System.out.println("Selection accuracy (100 random k): " +
                    String.format("%.1f%%", accuracy * 100));
            System.out.println("Expected depth: O(log n) ≈ " + (int)(Math.log(n)/Math.log(2)));
            System.out.println(metrics);
        }

        System.out.println("\n=== CLOSEST PAIR VALIDATION ===");
        for (int n : new int[]{100, 500, 1000, 2000}) {
            System.out.println("\n--- Points: " + n + " ---");

            Point[] points = TestUtils.createRandomPoints(n);
            Point[] pointsCopy = points.clone();

            if (n <= 2000) {
                double bruteForceDist = closestPair.bruteForceClosestPair(pointsCopy);
                double dcDist = closestPair.findClosestPair(points);
                boolean matches = Math.abs(bruteForceDist - dcDist) < 1e-9;
                System.out.println("Brute force: " + String.format("%.6f", bruteForceDist));
                System.out.println("D&C result: " + String.format("%.6f", dcDist));
                System.out.println("Results match: " + matches);
                System.out.println("D&C Metrics: " + metrics);
            } else {
                double dcDist = closestPair.findClosestPair(points);
                System.out.println("D&C result: " + String.format("%.6f", dcDist));
                System.out.println(metrics);
            }
        }

        System.out.println("\n PERFORMANCE SUMMARY ");
        System.out.println("All tests completed. Check individual results above.");
    }
}
