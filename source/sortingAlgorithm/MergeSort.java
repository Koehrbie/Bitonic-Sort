package source.sortingAlgorithm;

public class MergeSort implements SortingAlgorithm {

    @Override
    public void sort(int[] arr, int low, int cnt) throws IllegalArgumentException {
        if (low < cnt - 1) {
            int mid = (low + cnt) / 2;

            // Sort first half
            sort(arr, low, mid);

            // Sort second half
            sort(arr, mid, cnt);

            // Merge the sorted halves
            merge(arr, low, mid, cnt);
        }
    }

    private void merge(int[] arr, int low, int mid, int cnt) {
        int[] temp = new int[cnt - low];
        int i = low, j = mid, k = 0;

        while (i < mid && j < cnt) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i < mid) {
            temp[k++] = arr[i++];
        }

        while (j < cnt) {
            temp[k++] = arr[j++];
        }

        System.arraycopy(temp, 0, arr, low, temp.length);
    }
    
    @Override
    public String getNameString() {
        return "Merge Sort";
    }
}
