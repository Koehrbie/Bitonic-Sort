package source.sortingAlgorithm;

public interface SortingAlgorithm {
    void sort(int[] arr, int low, int cnt) throws IllegalArgumentException;
    String getNameString();
}
