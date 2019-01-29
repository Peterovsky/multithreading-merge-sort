import java.util.Random;

import static java.lang.Runtime.*;

public class Main {

    // Method that check if the array is sorted
    private static boolean isArraySorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i+1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        Random rand = new Random();
        int[] example = new int[10000000];
        int availableThreads = getRuntime().availableProcessors(); // Amount of available processors

        // Filling array with random integers in range 0-99
        for (int i = 0; i < example.length; i++) {
            example[i] = rand.nextInt(100);
        }

        int[] exampleCopy = example.clone();    // Copy of array to sort

        // Sorting using multi-threading
        MergeSort mergeSort1 = new MergeSort(example, 0, example.length - 1, availableThreads);
        long start = System.currentTimeMillis();
        mergeSort1.run();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Sorting time using " + availableThreads + " threads: " + elapsedTime);

        // Sorting using single thread
        MergeSort mergeSort2 = new MergeSort(example, 0, example.length - 1);
        start = System.currentTimeMillis();
        mergeSort2.mergeSortST(exampleCopy, 0, exampleCopy.length - 1);
        elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Sorting time using single thread: " + elapsedTime);

        System.out.println("Was multi-threading sorting done correctly?: " + isArraySorted(example));
        System.out.println("Was single-threading sorting done correctly? : " + isArraySorted(exampleCopy));

    }
}
