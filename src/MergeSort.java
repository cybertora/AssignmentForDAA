import java.util.Arrays;

public class MergeSort {
    private static final  int INSERTION_SORT_CUTOFF = 10;
    private final Metrics metrics;
    public MergeSort(Metrics metrics) {
        this.metrics = metrics;
    }

    public void sort(int[] array) {
        metrics.reset();
        long startTime = System.nanoTime();
        int[] buffer = new int[array.length];
        mergeSortHelper(array, 0, array.length - 1, buffer);
        metrics.executionTime = System.nanoTime() - startTime;
    }

    private void mergeSortHelper(int[] array, int low, int high, int[] buffer) {
        metrics.recursionDepth = Math.max(metrics.recursionDepth, metrics.recursionDepth + 1);
        if (right - left <= INSERTION_SORT_CUTOFF){
            insertionSort(array, left, right);
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSortHelper(arr, left, mid, buffer);
        mergeSortHelper(arr, mid + 1, right, buffer);
        merge(arr, left, mid, right, buffer);
    }

    private void merge(int[] arr, int left, int mid, int right, int[] buffer) {
        for (int i = left; i <= right; i++) {
            buffer[i] = arr[i];
        }
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            metrics.comparisons++;
            if (buffer[i] <= buffer[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = buffer[j++];
                metrics.swaps++;
            }
        }
        while (i <= mid) {
            arr[k++] = buffer[i++];
        }
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
