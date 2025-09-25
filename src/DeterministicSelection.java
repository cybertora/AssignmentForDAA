public class DeterministicSelection {
    private final Metrics metrics;
    public DeterministicSelection(Metrics metrics) {
        this.metrics = metrics;
    }

    public int select(int[] arr, int k) {
        metrics.reset();
        long startTime = System.nanoTime();
        int result = selectHelper(arr, 0, arr.length - 1, k - 1);
        metrics.executionTime = System.nanoTime() - startTime;
        return result;
    }

    private int selectHelper(int[] arr, int low, int high, int k) {
        metrics.recursionDepth = Math.max(metrics.recursionDepth, metrics.recursionDepth + 1);
        if (low == high) {
            return arr[low];
        }
        int pivotIndex = medianOfMedians(arr, low, high);
        pivotIndex = partition(arr, low, high);
        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return selectHelper(arr, low, pivotIndex - 1, k);
        } else {
            return selectHelper(arr, pivotIndex + 1, high, k);
        }
    }

    private int medianOfMedians(int[] arr, int low, int high) {
        int n = high - low + 1;
        int[] medians = new int[(n + 4) / 5];
        for (int i = 0; i < medians.length; i++) {
            int groupLow = low + i * 5;
            int groupHigh = Math.min(groupLow + 4, high);
            medians[i] = findMedian(arr, groupLow, groupHigh);
        }
        return selectHelper(medians, 0, medians.length - 1, medians.length / 2);
    }

    private int findMedian(int[] arr, int low, int high) {
        insertionSort(arr, low, high);
        return arr[(low + high) / 2];
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
}
