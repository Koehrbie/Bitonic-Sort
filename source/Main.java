package source;

import source.sortingAlgorithm.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        
        SortingAlgorithm[] Sorts = new SortingAlgorithm[4];
        Sorts[0] = new BitonicSort();
        Sorts[1] = new BitonicSortParallel();
        Sorts[2] = new TimSort();
        Sorts[3] = new MergeSortParallel();

        // Daten speichern
        List<Integer> arraySizes = new ArrayList<>();
        Map<String, List<Long>> results = new LinkedHashMap<>();
        
        // Initialisiere für jeden Algorithmus eine Liste
        for (SortingAlgorithm sort : Sorts) {
            results.put(sort.getNameString(), new ArrayList<>());
        }

        for(int round = 0; round < 15; round++) {

            int[] arr;
            arr = new int[1 << (round + 10)]; // 1024, 2048, 4096, 8192, 16384
            arraySizes.add(arr.length);

            for (int i = 0; i < arr.length; i++) {
                arr[i] = (int) (Math.random() * 10000); // Random integers between 0 and 9999
            }

            for(int i = 0; i < Sorts.length; i++) {
                if (Sorts[i] == null) {
                    System.out.println("Sorting algorithm at index " + i + " is not initialized.");
                    return;
                }

                // Kopie des Arrays für jeden Algorithmus
                int[] arrCopy = arr.clone();

                long startTime = System.currentTimeMillis();
                try {
                    Sorts[i].sort(arrCopy, 0, arrCopy.length);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error: " + e.getMessage());
                    return;
                }

                long endTime = System.currentTimeMillis();
                results.get(Sorts[i].getNameString()).add(endTime - startTime);
            }
        }

        // Tabelle anzeigen
        printTable(arraySizes, results, Sorts);
    }

    private static void printTable(List<Integer> arraySizes, Map<String, List<Long>> results, SortingAlgorithm[] sorts) {
        System.out.println("\n\n");
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         PERFORMANCE BENCHMARK RESULTS                                                              ║");
        System.out.println("╠═══════════════╦════════════════════════╦════════════════════════╦════════════════════════╦═════════════════════════╣");

        // Header
        System.out.printf("║ %-14s", "Array Size");
        for (SortingAlgorithm sort : sorts) {
            System.out.printf("║ %-23s", sort.getNameString());
        }
        System.out.println(" ║");

        // Separator
        System.out.println("╠═══════════════╬════════════════════════╬════════════════════════╬════════════════════════╬═════════════════════════╣");

        // Daten
        for (int i = 0; i < arraySizes.size(); i++) {
            System.out.printf("║ %-14d", arraySizes.get(i));
            for (SortingAlgorithm sort : sorts) {
                List<Long> times = results.get(sort.getNameString());
                System.out.printf("║ %20d ms", times.get(i));
            }
            System.out.println(" ║");
        }

        // Footer
        System.out.println("╚═══════════════╩════════════════════════╩════════════════════════╩════════════════════════╩═════════════════════════╝");
        System.out.println();
    }
}
