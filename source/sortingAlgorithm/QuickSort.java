package source.sortingAlgorithm;

public class QuickSort implements SortingAlgorithm {

    @Override
    public void sort(int[] arr, int low, int cnt) throws IllegalArgumentException {
        if (low < cnt - 1) {
            int pi = partition(arr, low, cnt);
            sort(arr, low, pi);
            sort(arr, pi + 1, cnt);
        }
    }

    private int partition(int[] arr, int low, int cnt) {
        int pivot = arr[cnt - 1];
        int i = low - 1;

        for (int j = low; j < cnt - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, cnt - 1);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Override
    public String getNameString() {
        return "Quick Sort";
    }

}
