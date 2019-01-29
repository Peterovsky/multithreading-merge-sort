public class MergeSort extends Thread {

    private int[] array;
    private int l;
    private int r;
    private int availableThreads;

    public MergeSort(int[] array, int l, int r) {
        this.array = array;
        this.l = l;
        this.r = r;
        this.availableThreads = 1;
    }

    public MergeSort(int[] array, int l, int r, int availableThreads) {
        this.array = array;
        this.l = l;
        this.r = r;
        this.availableThreads = availableThreads;
    }

    private void merge(int[] array, int l, int m, int r) {

        int leftLength = m - l + 1;
        int rightLength = r - m;

        int[] leftArray = new int[leftLength];
        int[] rightArray = new int[rightLength];

        for (int i = 0; i < leftLength; i++) {
            leftArray[i] = array[l + i];
        }
        for (int j = 0; j < rightLength; j++) {
            rightArray[j] = array[m + 1 + j];
        }

        int i = 0, j = 0, k = l;

        while (i < leftLength && j < rightLength) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < leftLength) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < rightLength) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // Multi-thread merge sort implementation
    private void mergeSortMT(int[] array, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            if (availableThreads >= 2) {
                // Splitting array into two halves and creating new threads to merge sort each one
                MergeSort msLeft = new MergeSort(array, l, m, availableThreads - 1);
                MergeSort msRight = new MergeSort(array, m + 1, r, availableThreads - 1);
                msLeft.start();
                msRight.start();
                try {
                    msLeft.join();
                    msRight.join();
                } catch (InterruptedException e) {
                }
            } else {
                mergeSortST(array, l, m);
                mergeSortST(array, m + 1, r);
            }
            // Merging two arrays into one
            merge(array, l, m, r);
        }
    }

    // Single-thread merge sort implementation
    public void mergeSortST(int[] array, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;

            mergeSortST(array, l, m);
            mergeSortST(array, m + 1, r);

            merge(array, l, m, r);
        }
    }

    public void run() {
        mergeSortMT(array, l, r);
    }

}
