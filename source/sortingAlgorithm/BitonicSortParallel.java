package source.sortingAlgorithm;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class BitonicSortParallel implements SortingAlgorithm {
    
    // Threshold: Nur Parallelisierung bei größeren Arrays (reduziert Thread-Overhead)
    private static final int THREAD_THRESHOLD = 1024;
    private static final ForkJoinPool pool = ForkJoinPool.commonPool();

    @Override
    public void sort(int[] arr, int low, int cnt) throws IllegalArgumentException {
        if (powerOfTwo(cnt) == false) {
            throw new IllegalArgumentException("Count must be a power of 2");
        } else {
            BitonicSortTask task = new BitonicSortTask(arr, low, cnt, true);
            pool.invoke(task);
        }
    }

    public boolean powerOfTwo(int n) {
        return (n & (n - 1)) == 0 && n > 0;
    }

    private class BitonicSortTask extends RecursiveAction {
        private int[] arr;
        private int low;
        private int cnt;
        private boolean dir;

        BitonicSortTask(int[] arr, int low, int cnt, boolean dir) {
            this.arr = arr;
            this.low = low;
            this.cnt = cnt;
            this.dir = dir;
        }

        @Override
        protected void compute() {
            if (cnt > 1) {
                int n = cnt / 2;

                BitonicSortTask task1 = new BitonicSortTask(arr, low, n, true);
                BitonicSortTask task2 = new BitonicSortTask(arr, low + n, n, false);
                
                if (cnt >= THREAD_THRESHOLD) {
                    invokeAll(task1, task2);
                } else {
                    task1.compute();
                    task2.compute();
                }

                BitonicMergeTask mergeTask = new BitonicMergeTask(arr, low, cnt, dir);
                mergeTask.compute();
            }
        }
    }

    private class BitonicMergeTask extends RecursiveAction {
        private int[] arr;
        private int low;
        private int cnt;
        private boolean dir;

        BitonicMergeTask(int[] arr, int low, int cnt, boolean dir) {
            this.arr = arr;
            this.low = low;
            this.cnt = cnt;
            this.dir = dir;
        }

        @Override
        protected void compute() {
            if (cnt > 1) {
                int n = cnt / 2;

                for (int i = low; i < low + n; i++) {
                    if (dir == (arr[i] > arr[i + n])) {
                        swap(arr, i, i + n);
                    }
                }

                BitonicMergeTask task1 = new BitonicMergeTask(arr, low, n, dir);
                BitonicMergeTask task2 = new BitonicMergeTask(arr, low + n, n, dir);
                
                if (cnt >= THREAD_THRESHOLD) {
                    invokeAll(task1, task2);
                } else {
                    task1.compute();
                    task2.compute();
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
        return "Bitonic Sort parallel";
    }
}
