package source.sortingAlgorithm;

public class JavaSort extends BitonicSort {

    @Override
    public void sort(int[] arr, int low, int cnt) throws IllegalArgumentException {
        java.util.Arrays.sort(arr, low, cnt);
    }

    @Override
    public String getNameString() {
        return "Java's Built-in Sort";
    }
    
}
