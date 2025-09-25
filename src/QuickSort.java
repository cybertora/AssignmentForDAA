import java.util.Random;

public class QuickSort {
    private static final int INSERTION_SORT_CUTOFF = 10;
    private final Metrics metrics;
    public QuickSort(Metrics metrics) {
        this.metrics = metrics;
    }

    public void  sort(int[] arr) {
        metrics.reset();
        long startTime = System.nanoTime();
        quickSortHelper(arr, 0, arr.length - 1);
        metrics.executionTime = System.nanoTime() - startTime;
    }

    private void quickSortHelper(int[] arr, int low, int high) {
        metrics.recursionDepth = Math.max(metrics.recursionDepth, metrics.recursionDepth + 1);
        if (high - low <= INSERTION_SORT_CUTOFF) {
            insertionSort(arr, low, high);
            return;
        }
        while (low < high) {
            int pivotIndex = randomizedPartition(arr, low, high);
            int leftSize = pivotIndex - low;
            int rightSize = high - pivotIndex + 1;
            if (leftSize < rightSize) {
                quickSortHelper(arr, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quickSortHelper(arr, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    private int randomizedPartition(int[] arr, int low, int high) {
        Random rand = new Random();
        int pivotIndex = low + rand.nextInt(high - low + 1);
        swap(arr, pivotIndex, high);
        return partition(arr, low, high);
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            metrics.comparisons++;
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        metrics.swaps++;
    }

    private void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                metrics.comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    metrics.swaps++;
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }
}
