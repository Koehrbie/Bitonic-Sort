package source.sortingAlgorithm;

public class BitonicSort implements SortingAlgorithm {

    @Override
    public void sort(int[] arr, int low, int cnt) throws IllegalArgumentException {
        sort(arr, low, cnt, true);
    }

    private void sort(int[] arr, int low, int cnt, boolean dir) throws IllegalArgumentException {
        if (powerOfTwo(cnt) == false) {
            throw new IllegalArgumentException("Count must be a power of 2");
        } else {
            bitonicSort(arr, low, cnt, dir);
        }
    }

    public boolean powerOfTwo(int n) {
        return (n & (n - 1)) == 0 && n > 0;
    }

    public void bitonicSort(int[] arr, int low, int cnt, boolean dir) {
        if (cnt > 1) {
            int n = cnt / 2;

            // Sort first half in ascending order
            bitonicSort(arr, low, n, true);

            // Sort second half in descending order
            bitonicSort(arr, low + n, n, false);

            // Merge the whole sequence in ascending or descending order
            bitonicMerge(arr, low, cnt, dir);
        }
    }

    public void bitonicMerge(int[] arr, int low, int cnt, boolean dir) {
        if (cnt > 1) {
            int n = cnt / 2;

            for (int i = low; i < low + n; i++) {
                if (dir == (arr[i] > arr[i + n])) {
                    swap(arr, i, i + n);
                }
            }

            bitonicMerge(arr, low, n, dir);
            bitonicMerge(arr, low + n, n, dir);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    @Override
    public String getNameString() {
        return "Bitonic Sort";
    }
}
