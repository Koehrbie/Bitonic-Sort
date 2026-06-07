package source.sortingAlgorithm;

public class BubbleSort implements SortingAlgorithm {

    @Override
    public void sort(int[] arr, int low, int cnt) throws IllegalArgumentException {
        for (int i = low; i < cnt - 1; i++) {
            for (int j = low; j < cnt - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Override
    public String getNameString() {
        return "Bubble Sort";
    }
}
