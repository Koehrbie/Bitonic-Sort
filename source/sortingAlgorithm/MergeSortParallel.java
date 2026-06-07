package source.sortingAlgorithm;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MergeSortParallel implements SortingAlgorithm {

    private static final int THREAD_THRESHOLD = 2048;
    private static final ForkJoinPool pool = ForkJoinPool.commonPool();

    @Override
    public void sort(int[] arr, int low, int cnt) throws IllegalArgumentException {
        if (low < 0 || cnt <= 0 || low + cnt > arr.length) {
            throw new IllegalArgumentException("Invalid low or count parameters");
        }
        MergeSortTask task = new MergeSortTask(arr, low, low + cnt - 1);
        pool.invoke(task);
    }

    private class MergeSortTask extends RecursiveAction {
        private int[] arr;
        private int left;
        private int right;

        MergeSortTask(int[] arr, int left, int right) {
            this.arr = arr;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (left < right) {
                int mid = left + (right - left) / 2;

                if (right - left + 1 >= THREAD_THRESHOLD) {
                    MergeSortTask leftTask = new MergeSortTask(arr, left, mid);
                    MergeSortTask rightTask = new MergeSortTask(arr, mid + 1, right);
                    invokeAll(leftTask, rightTask);
                } else {
                    mergeSortSequential(arr, left, mid);
                    mergeSortSequential(arr, mid + 1, right);
                }

                merge(arr, left, mid, right);
            }
        }
    }

    private void mergeSortSequential(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortSequential(arr, left, mid);
            mergeSortSequential(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) {
            arr[k++] = L[i++];
        }

        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    @Override
    public String getNameString() {
        return "Merge Sort Parallel";
    }
}
