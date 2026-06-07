package source.sortingAlgorithm;

public class TimSort implements SortingAlgorithm {

    @Override
    public void sort(int[] arr, int low, int cnt) throws IllegalArgumentException {
        int RUN = 32;
        for (int i = low; i < cnt; i += RUN) {
            insertionSort(arr, i, Math.min((i + RUN - 1), (cnt - 1)));
        }
        for (int size = RUN; size < cnt; size *= 2) {
            for (int left = low; left < cnt; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (cnt - 1));
                if (mid < right) {
                    merge(arr, left, mid, right);
                }
            }
        }
    }

    private void insertionSort(int[] arr, int i, int min) {
        for (int j = i + 1; j <= min; j++) {
            int key = arr[j];
            int k = j - 1;
            while (k >= i && arr[k] > key) {
                arr[k + 1] = arr[k];
                k--;
            }
            arr[k + 1] = key;
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int len1 = mid - left + 1, len2 = right - mid;
        int[] leftArr = new int[len1];
        int[] rightArr = new int[len2];

        for (int i = 0; i < len1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int i = 0; i < len2; i++) {
            rightArr[i] = arr[mid + 1 + i];
        }

        int i = 0, j = 0, k = left;
        while (i < len1 && j < len2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < len1) {
            arr[k++] = leftArr[i++];
        }
        while (j < len2) {
            arr[k++] = rightArr[j++];
        }
    }

    @Override
    public String getNameString() {
        return "TimSort";
    }
    
}
